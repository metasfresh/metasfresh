#!/bin/sh

# Copy Database after backup
# $Id: myDBcopyTemplate.sh,v 1.1 2002/10/07 01:23:32 jjanke Exp $

DATE=`date +%Y%m%d_%H%M%S`
mv $ADEMPIERE_HOME/data/ExpDat.jar $ADEMPIERE_HOME/data/ExpDat$DATE.jar
echo copy $ADEMPIERE_HOME/data/ExpDat$DATE.jar to backup media

sleep 30
