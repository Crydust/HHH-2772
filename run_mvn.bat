@call mvn clean compile test -Phibernate > hibernate.txt 2>&1
@call mvn clean compile test -Peclipselink > eclipselink.txt 2>&1
@call mvn clean compile test -Phibernate,hibernate-4-snapshot > hibernate-4-snapshot.txt 2>&1
@call mvn clean compile test -Phibernate,hibernate-5-snapshot > hibernate-5-snapshot.txt 2>&1