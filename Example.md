# Introduction #

Example of use

# Details #

You can now use this wonderfull framework with maven.

# Download the sources.
# Install some libraries by hand (included in official version of openxava).
# Install the application.

Here is an example pom.xml that use this version:

```

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.foo.mywar</groupId>
	<artifactId>mywar</artifactId>
	<name>mywar</name>
	<packaging>war</packaging>
	<version>0.1.0</version>


	<properties>
		<openxava.version>4.1.2</openxava.version>
		<slf4j.version>1.6.1</slf4j.version>
		<maven.compiler.source>1.6</maven.compiler.source>
		<maven.compiler.target>1.6</maven.compiler.target>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>


	<repositories>

		<repository>
			<id>ibiblio</id>
			<name>Ibilbio</name>
			<url>http://mirrors.ibiblio.org/pub/mirrors/maven2/</url>
		</repository>

	</repositories>


	<dependencies>

		<dependency>
			<groupId>org.openxava</groupId>
			<artifactId>openxava</artifactId>
			<version>${openxava.version}</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>4.1.0.Final</version>
		</dependency>

		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>servlet-api</artifactId>
			<version>2.4</version>
			<scope>provided</scope>
		</dependency>

		<!-- Es la version hibernate-validator 3.1.0.GA pero se necesita en runtime. 
			Si no usariamos una version tan nueva de hibernate no es necesario, pero 
			necesitamos el validador tambien 4.1.0.Final Hay que copiar los jar de org.hibernate/hibernate-validator 
			a org.hibernate/hibernate-classic-validator de la version 3.1.0.GA -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-classic-validator</artifactId>
			<version>3.1.0.GA</version>
		</dependency>

		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>${slf4j.version}</version>
		</dependency>

		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>${slf4j.version}</version>
		</dependency>

	</dependencies>

	<build>

		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>${maven.compiler.source}</source>
					<target>${maven.compiler.target}</target>
					<encoding>${project.build.sourceEncoding}</encoding>
				</configuration>
			</plugin>




			<!-- Other -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>2.1.1</version>
				<configuration>
					<webResources>
						<resource>
							<directory>${basedir}/src/main/webapp/WEB-INF</directory>
							<filtering>true</filtering>
							<targetPath>WEB-INF</targetPath>
							<includes>
								<include>**/jetty-env.xml</include>
							</includes>
						</resource>
					</webResources>
				</configuration>
			</plugin>

			<!-- IDE -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-eclipse-plugin</artifactId>
				<!-- WARNING: Do not use 2.6 or above due to http://jira.codehaus.org/browse/MECLIPSE-538 -->
				<version>2.8</version>
				<configuration>
					<downloadSources>true</downloadSources>
					<downloadJavadocs>false</downloadJavadocs>
					<wtpversion>2.0</wtpversion>
				</configuration>
			</plugin>

			<!-- Site -->

			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>tomcat-maven-plugin</artifactId>
			</plugin>

			<plugin>
				<groupId>org.mortbay.jetty</groupId>
				<artifactId>maven-jetty-plugin</artifactId>				
				<version>6.1.23</version>
				<configuration>
				<dependencies>
					<dependency>
						<groupId>commons-logging</groupId>
						<artifactId>commons-logging</artifactId>
						<version>1.1</version>
					</dependency>
					<dependency>
						<groupId>javax.servlet</groupId>
						<artifactId>servlet-api</artifactId>
						<version>2.5</version>
					</dependency>
					<!-- How obtain: http://stackoverflow.com/questions/1074869/find-jdbc-driver-in-maven-repository -->
					<dependency>
						<groupId>com.oracle</groupId>
						<artifactId>ojdbc14</artifactId>
						<version>10.2.0.3.0</version>
					</dependency>
				</dependencies>
			</plugin>
		</plugins>
	</build>

</project>
```