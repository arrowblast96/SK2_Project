#include <sys/types.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <pthread.h>
#include <cmath>
#include "Session.hpp"
#include <string>       // std::string
#include <iostream>     // std::cout
#include <sstream>      // std::istringstream
#define SERVER_PORT 1234
#define QUEUE_SIZE 5

using namespace std;


pthread_mutex_t example_mutex = PTHREAD_MUTEX_INITIALIZER;

struct thread_data_t
{

int session_number;
};

vector<Session *> sesja;
vector<pthread_t *> threads;




void *ThreadBehavior(void *t_data)
{
	srand(time(NULL));

    pthread_detach(pthread_self());
    struct thread_data_t *th_data = (struct thread_data_t*)t_data;


    while(1)
    {


    	for(int i=0;i<sesja[th_data->session_number]->sockets.size();)
    	{
    		while(sesja[th_data->session_number]->sockets.size()==1);
    		pthread_mutex_lock(&sesja[th_data->session_number]->mutex);
    		sesja[th_data->session_number]->buf[i]="Twoja tura\n";
    		int check_connection=0;
    		check_connection=write(sesja[th_data->session_number]->sockets[i],sesja[th_data->session_number]->buf[i].c_str(),1000);
    		if(check_connection==-1)
    		{
    			pthread_mutex_unlock(&sesja[th_data->session_number]->mutex);
    			close(sesja[th_data->session_number]->sockets[i]);
    			sesja[th_data->session_number]->sockets.erase(sesja[th_data->session_number]->sockets.begin()+i);
    			sesja[th_data->session_number]->buf.erase(sesja[th_data->session_number]->buf.begin()+i);
    			printf("Usunięty\n");

    			continue;
    		}
    		char *reader=new char[1000];
    		check_connection=read(sesja[th_data->session_number]->sockets[i],reader,1000);
    		if(check_connection==0)
    		{
    			pthread_mutex_unlock(&sesja[th_data->session_number]->mutex);
    			close(sesja[th_data->session_number]->sockets[i]);
    			sesja[th_data->session_number]->sockets.erase(sesja[th_data->session_number]->sockets.begin()+i);
    			sesja[th_data->session_number]->buf.erase(sesja[th_data->session_number]->buf.begin()+i);
    			printf("Usunięty\n");
    		    continue;
    		}
    		sesja[th_data->session_number]->buf[i].clear();

    		int wylosowany=1+rand()%6;
    		stringstream input;
    		input << wylosowany;
    		sesja[th_data->session_number]->buf[i]=input.str();

    		write(sesja[th_data->session_number]->sockets[i],sesja[th_data->session_number]->buf[i].c_str(),1000);
    		pthread_mutex_unlock(&sesja[th_data->session_number]->mutex);
    		i++;
    	}
    	

    }

    pthread_exit(NULL);
}



void handleConnection(int connection_socket_descriptor,int session_number) {

    int create_result = 0;




   struct thread_data_t *t_data=(struct thread_data_t *)malloc(sizeof(struct thread_data_t));

   t_data->session_number=session_number;
   if(session_number>(int)threads.size()-1)
   {
	   	  threads.push_back(new pthread_t);
   }

   create_result = pthread_create(threads[session_number], NULL, ThreadBehavior, (void *)t_data);
    if (create_result){
       printf("Błąd przy próbie utworzenia wątku, kod błędu: %d\n", create_result);
       exit(-1);
    }
    cout<<"Stworzyłem\n";

}

int main(int argc, char* argv[])
{
   int server_socket_descriptor;
   int connection_socket_descriptor;
   int bind_result;
   int listen_result;
   char reuse_addr_val = 1;
   struct sockaddr_in server_address;


   
   memset(&server_address, 0, sizeof(struct sockaddr));
   server_address.sin_family = AF_INET;
   server_address.sin_addr.s_addr = htonl(INADDR_ANY);
   server_address.sin_port = htons(SERVER_PORT);

   server_socket_descriptor = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
   if (server_socket_descriptor < 0)
   {
       fprintf(stderr, "%s: Błąd przy próbie utworzenia gniazda..\n", argv[0]);
       exit(1);
   }
   setsockopt(server_socket_descriptor, SOL_SOCKET, SO_KEEPALIVE, (char*)&reuse_addr_val, sizeof(reuse_addr_val));

   bind_result = bind(server_socket_descriptor, (struct sockaddr*)&server_address, sizeof(struct sockaddr));
   if (bind_result < 0)
   {
       fprintf(stderr, "%s: Błąd przy próbie dowiązania adresu IP i numeru portu do gniazda.\n", argv[0]);
       exit(1);
   }

   listen_result = listen(server_socket_descriptor, QUEUE_SIZE);
   if (listen_result < 0) {
       fprintf(stderr, "%s: Błąd przy próbie ustawienia wielkości kolejki.\n", argv[0]);
       exit(1);
   }
   int clients=0;
   bool ok=true;
   while((connection_socket_descriptor = accept(server_socket_descriptor, NULL, NULL)))
   {
	   if(clients%4==0)
	   {
		   sesja.push_back(new Session());
	   }
	sesja[floor(clients/4)]->sockets.push_back(connection_socket_descriptor);
	sesja[floor(clients/4)]->buf.push_back("");
       if (connection_socket_descriptor < 0)
       {
           fprintf(stderr, "%s: Błąd przy próbie utworzenia gniazda dla połączenia.\n", argv[0]);
           exit(1);
       }
    printf("%d",(int)floor(clients/4));

    handleConnection(sesja[floor(clients/4)]->sockets[clients%4],floor(clients/4));

	clients++;

   }
   cout<<"Zerwano polaczenie\n";
   close(server_socket_descriptor);
   return(0);
}
