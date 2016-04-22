comp:
	javac *.java

rmi:
	rmiregistry 54321&

controller:
	java -Djava.security.policy=servidor.permisos ControllerServer 54321

AP1:
	java -Djava.security.policy=servidor.permisos ApServer localhost 54321 1 0 0 0

AP2:
	java -Djava.security.policy=servidor.permisos ApServer localhost 54321 2 2 0 0

AP3:
	java -Djava.security.policy=servidor.permisos ApServer localhost 54321 3 2 2 0

AP4:
	java -Djava.security.policy=servidor.permisos ApServer localhost 54321 4 0 2 0

station1:
	java -Djava.security.policy=servidor.permisos StationImpl localhost 54321 1 01:01:01:01:01:01 host1 0 0 0

station2:
	java -Djava.security.policy=servidor.permisos StationImpl localhost 54321 1 02:02:02:02:02:02 host2 1 1 1

station3:
	java -Djava.security.policy=servidor.permisos StationImpl localhost 54321 1 03:03:03:03:03:03 host3 1 -4 3