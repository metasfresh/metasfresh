# Module compiling script
# Ported from Windows script Marek Mosiewicz<marek.mosiewicz@jotel.com.pl>


SAVED_DIR=`pwd`			#save current dir
cd `dirname $0`/../utils_dev	#change dir to place where script resides - doesn not work with sym links
UTILS_DEV=`pwd`			#this is adempiere source
cd $SAVED_DIR			#back to the saved directory

.  $UTILS_DEV/myDevEnv.sh	#call environment
echo done
if [ ! $ADEMPIERE_ENV==Y ] ; then
    echo "Can't set developemeent environemnt - check myDevEnv.sh"
    exit 1
fi

echo running Ant
$JAVA_HOME/bin/java -Dant.home="." $ANT_PROPERTIES org.apache.tools.ant.Main
