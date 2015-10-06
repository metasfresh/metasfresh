#!/bin/sh

# This formats all the SQL files in the specified directory so that
# they can be executed by psql. There are two modes -- a 'testing'
# mode (the default mode -- this strips out all the "commit" statements)
# and a commit mode for deployment. You need to pipe the output of this
# script into sqlplus, for example:
# ./migrate_postgresql.sh 313-314 commit | psql -U adempiere -d adempiere > 313-314.lst

# Original contribution by by Chris Farley - northernbrewer
# Adapted to postgresql by Carlos Ruiz - globalqss

# CarlosRuiz - added multidirectory management 2008/02/20

if [ -z "$1" ]; then
   echo "Usage: $0 [DIRECTORY ... DIRECTORY] [commit]"
   exit 0
fi
DIRINI=$1
COMMIT=0
while [ $# -gt 0 ]
do
    DIR=$1
    shift
    if [ "$DIR" = "commit" ]; then
       COMMIT=1
    else
	ls $DIR/postgresql/*.sql | while read file; do
           echo "SELECT '`basename "$file"`' AS Filename;"
           echo
           cat "$file" | dos2unix | sed 's/commit[ ]*;//I'
           echo
           echo
        done
    fi
done
if [ -d $DIRINI/../processes_post_migration/postgresql ]
then
   ls $DIRINI/../processes_post_migration/postgresql/*.sql | while read file; do
      echo "SELECT '`basename $file`' AS Filename;"
      echo
      cat $file | dos2unix | sed 's/commit[ ]*;//I'
      echo
      echo
   done
fi
if [ -d $DIRINI/../my_processes_post_migration/postgresql ]
then
   ls $DIRINI/../my_processes_post_migration/postgresql/*.sql | while read file; do
      echo "SELECT '`basename "$file"`' AS Filename;"
      echo
      cat "$file" | dos2unix | sed 's/commit[ ]*;//I'
      echo
      echo
   done
fi
if [ $COMMIT -eq 1 ]
then
    echo "COMMIT;"
else
    echo "ROLLBACK;"
fi
echo
echo "\quit"
