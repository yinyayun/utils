#!/bin/sh
ORIGIN_DIR=`pwd`
EXE="$0"
FILENAME=`basename "$EXE"`
EXEDIR=`dirname "$EXE"`
PIDFILE=$EXEDIR/../server.pid
STARTEDFILE=$EXEDIR/../started.log
MAINCLASS="Mainclass"

if [ -d "$JAVA7_HOME" ];
then
	export JAVA_HOME=$JAVA7_HOME
fi
echo JAVA_HOME:$JAVA_HOME

# build LIB string
LIB="$EXEDIR/../lib"
echo "LIB=$LIB"
PROJECT_NAME="project name"
#priority to load $PROJECT_NAME*.jar
for jar in $LIB/*.jar; do
    echo "$jar" |grep -q "lib/$PROJECT_NAME"
    if [ $? -eq 0 ]
      then
        SORT_LIB=$jar:$SORT_LIB
      else
        SORT_LIB=$SORT_LIB:$jar
    fi
done
LIB=$SORT_LIB

#get absolute path of bin
APP_ROOT_DIR="$EXEDIR/../"
cd $APP_ROOT_DIR
APP_ROOT=`pwd`
cd $ORIGIN_DIR
#end


echo $APP_ROOT
echo `pwd`
echo $STARTEDFILE

case $1 in
start)
    echo  "Starting app ... "
    rm -f $STARTEDFILE
    $JAVA_HOME/bin/java -Xms${Xms} -Xmx${Xmx} -Xmn${Xmn} -XX:OnOutOfMemoryError="sh $APP_ROOT/bin/$FILENAME stop" -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$APP_ROOT ${other_jvm_params} -cp $LIB $MAINCLASS  $APP_ROOT &
    echo $! > $PIDFILE
    #check started
    for ((i=0;i<300;i++));do
    {
      sleep 2

      if [ -f $STARTEDFILE ]
      then
        echo STARTED
        exit 0
      fi
    }
    done

    echo "FAILED TO START"
    kill  $(cat $PIDFILE)
    exit 1
    #
    ;;
stop)
    echo "Stopping app ... "
    if [ ! -f $PIDFILE ]
    then
    echo "error: count not find file $PIDFILE"
    exit 1
    else
    kill  $(cat $PIDFILE)
    rm $PIDFILE
    echo STOPPED
    fi
    ;;
*)
    echo "Usage: $0 {start|stop|restart|}" >&2

esac

