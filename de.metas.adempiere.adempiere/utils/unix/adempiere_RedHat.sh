#!/bin/bash
#
# FileName:	adempiere.init
# Description:	adempiere erp software startup and shutdown
# Vendor:	K.K. Alice
# Created:	05. April 2004
# Author:	S. Christians
# Updated:	Carlos Ruiz - globalqss - tested in Redhat FC4
#               Carlos Ruiz - globalqss - added timeout review of the log file
#
# FileTarget:	/etc/init.d/adempiere
# FileOwner:	root.root
# FilePerms:	0755
#
# chkconfig:	2345 97 06
# $Id: adempiere,v 1.1 2006/03/16 05:00:28 cruiz Exp $

# initialization
# adjust these variables to your environment
EXECDIR=/home/adempiere/Adempiere
ENVFILE=/home/adempiere/.bash_profile
ADEMPIEREUSER=adempiere
# STOPMESSAGE="Halting VM" # Message when using java 5
STOPMESSAGE="INFO.*\[Server\].*Shutting down the server" # Message when using java 6

. /etc/rc.d/init.d/functions
 
RETVAL=0
ADEMPIERESTATUS=
MAXITERATIONS=60 # 2 seconds every iteration, max wait 2 minutes)

getadempierestatus() {
    ADEMPIERESTATUSSTRING=$(ps ax | grep -v grep | grep $EXECDIR)
    echo $ADEMPIERESTATUSSTRING | grep -q $EXECDIR
    ADEMPIERESTATUS=$?
}

start () {
    getadempierestatus
    if [ $ADEMPIERESTATUS -eq 0 ] ; then
	echo "adempiere is already running"
	return 1
    fi
    echo -n "Starting ADempiere ERP: "
    source $ENVFILE 
    export LOGFILE=$ADEMPIERE_HOME/jboss/server/adempiere/log/adempiere_`date +%Y%m%d%H%M%S`.log
    su $ADEMPIEREUSER -c "mkdir -p $ADEMPIERE_HOME/jboss/server/adempiere/log"
    su $ADEMPIEREUSER -c "cd $EXECDIR/utils;$EXECDIR/utils/RUN_Server2.sh &> $LOGFILE &"
    RETVAL=$?
    if [ $RETVAL -eq 0 ] ; then
	# wait for server to be confirmed as started in logfile
	STATUSTEST=0
	ITERATIONS=0
	while [ $STATUSTEST -eq 0 ] ; do
	    sleep 2
	    tail -n 5 $LOGFILE | grep -q 'INFO.*\[Server\].*Started in' && STATUSTEST=1
	    echo -n "."
	    ITERATIONS=`expr $ITERATIONS + 1`
	    if [ $ITERATIONS -gt $MAXITERATIONS ]
	    then
	        break
	    fi
	done
	if [ $STATUSTEST -eq 0 ]
	then
	    echo "Service hasn't started within the timeout allowed, please review file $LOGFILE to see the status of the service"
	    echo_warning
	else
	    echo_success
	fi
	echo
    else
	echo_failure
	echo
    fi
    return $RETVAL
}

stop () {
    getadempierestatus
    if [ $ADEMPIERESTATUS -ne 0 ] ; then
	echo "adempiere is already stopped"
	return 1
    fi
    echo -n "Stopping ADempiere ERP: "
    source $ENVFILE 
    export LASTLOG=`ls -t $ADEMPIERE_HOME/jboss/server/adempiere/log/adempiere_??????????????.log | head -1`
    su $ADEMPIEREUSER -c "cd $EXECDIR/utils;$EXECDIR/utils/RUN_Server2Stop.sh &> /dev/null &"
    RETVAL=$?
    if [ $RETVAL -eq 0 ] ; then
	# wait for server to be confirmed as halted in logfile
	STATUSTEST=0
	ITERATIONS=0
	while [ $STATUSTEST -eq 0 ] ; do
	    sleep 2
	    tail -n 9 $LASTLOG | grep -q "$STOPMESSAGE" && STATUSTEST=1
	    echo -n "."
	    ITERATIONS=`expr $ITERATIONS + 1`
	    if [ $ITERATIONS -gt $MAXITERATIONS ]
	    then
	        break
	    fi
	done
	if [ $STATUSTEST -eq 0 ]
	then
	    echo "Service hasn't stopped within the timeout allowed, please review file $LASTLOG to see the status of the service"
	    echo_warning
	else
	    echo_success
	fi
	echo
    else
	echo_failure
	echo
    fi
    return $RETVAL
}

restart () {
    stop
    sleep 1
    start
}

condrestart () {
    getadempierestatus
    if [ $ADEMPIERESTATUS -eq 0 ] ; then
	restart
    fi
}

rhstatus () {
    getadempierestatus
    if [ $ADEMPIERESTATUS -eq 0 ] ; then
	echo
	echo "adempiere is running:"
	ps ax | grep -v grep | grep $EXECDIR | sed 's/^[[:space:]]*\([[:digit:]]*\).*:[[:digit:]][[:digit:]][[:space:]]\(.*\)/\1 \2/'
	echo
    else
	echo "adempiere is stopped"
    fi
}

case "$1" in
    start)
	start
        ;;
    stop)
	stop
        ;;
    reload)
	restart
	;;
    restart)
	restart
	;;
    condrestart)
	condrestart
	;;
    status)
	rhstatus
	;;
    *)
        echo $"Usage: $0 {start|stop|reload|restart|condrestart|status}"
        exit 1
esac
 
exit 0
