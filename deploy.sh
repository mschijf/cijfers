cd /Users/mschijf/PriveSources/cijfers
mvn clean package 
ssh pi rm cijfers/cijfers*.jar
sftp pi <<< $'mput target/cijfers*.jar cijfers/'
ssh pi ./cijfers/start.sh

