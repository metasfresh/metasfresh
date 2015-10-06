#!/bin/sh

echo Start PostgreSQL DB Service

# $Id: Start.sh,v 1.3 2002/04/26 03:20:09 jjanke Exp $

# IPC demon may be required for cygwin
# ipc-daemon&

pg_ctl -o "-i" -l $PGLOG start
