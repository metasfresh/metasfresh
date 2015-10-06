@Title Start PostgreSQL DB Service

@Rem $Id: Start.bat,v 1.4 2002/10/01 21:25:19 jjanke Exp $

@Rem IPC demon may be required for cygwin
@Rem ipc-daemon&

pg_ctl -o "-i" -l $PGLOG start

@pause
