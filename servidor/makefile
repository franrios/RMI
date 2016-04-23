comp: ApImpl.java IpBank.java  ShutdownHelper.java Ap.java ControllerImpl.java \
	StationImpl.java ApServer.java Controller.java Position.java Station.java \
	Association.java ControllerServer.java
	javac *.java
	cp Station.java StationImpl.java Station.class StationImpl.class \
	Position.class Ap.class ShutdownHelper.class ../cliente

rmi:
	rmiregistry 54321&

controller: Controller.class ControllerImpl.class ControllerServer.class \
	Station.class Ap.class Association.class Position.class IpBank.class
	java -Djava.security.policy=servidor.permisos ControllerServer 54321

ap1: Ap.class ApImpl.class ApServer.class  Station.class Association.class \
	Position.class Controller.class ShutdownHelper.class
	java -Djava.security.policy=servidor.permisos ApServer localhost 54321 1 0 0 0

ap2: Ap.class ApImpl.class ApServer.class  Station.class Association.class \
	Position.class Controller.class ShutdownHelper.class
	java -Djava.security.policy=servidor.permisos ApServer localhost 54321 2 2 0 0

ap3: Ap.class ApImpl.class ApServer.class  Station.class Association.class \
	Position.class Controller.class ShutdownHelper.class
	java -Djava.security.policy=servidor.permisos ApServer localhost 54321 3 2 2 0

ap4: Ap.class ApImpl.class ApServer.class  Station.class Association.class \
	Position.class Controller.class ShutdownHelper.class
	java -Djava.security.policy=servidor.permisos ApServer localhost 54321 4 0 2 0

station1: Station.class StationImpl.class Ap.class Position.class ShutdownHelper.class
	java -Djava.security.policy=cliente.permisos StationImpl localhost 54321 1 01:01:01:01:01:01 host1 0 0 0

station2: Station.class StationImpl.class Ap.class Position.class ShutdownHelper.class
	java -Djava.security.policy=cliente.permisos StationImpl localhost 54321 1 02:02:02:02:02:02 host2 1 1 1

station3: Station.class StationImpl.class Ap.class Position.class ShutdownHelper.class
	java -Djava.security.policy=cliente.permisos StationImpl localhost 54321 1 03:03:03:03:03:03 host3 1 -4 3

clean:
	rm *.class
	rm ../cliente/*.class