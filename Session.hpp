/*
 * Session.hpp
 *
 *  Created on: 7 Nov 2017
 *      Author: osboxes
 */

#ifndef SESSION_HPP_
#define SESSION_HPP_
#include <string>
#include <pthread.h>
#include <vector>
using namespace std;

class Session
{
public:
	vector<int> sockets;
	vector<string> buf;
	pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
	Session()
	{

	};

};


#endif /* SESSION_HPP_ */
