# CarParkingApplication - Team4

## Team Members: 
1. Aditya Maurya 
2. Rituj Singh
3. Ishan Khanduja
4. Kaumudi Tyagi
5. Mosin Sheikh



# CarParkingApplication

Car Parking Application is built using Spring Boot and Deployed in AWS using Docker and Kubernetes.

Car Parking Application have 4 Modules
1. Admin Module
2. Customer Module
3. Manager Module
4. Security Module

### ✔ Steps to Run CarParkingApplication 

1. Clone the Repository and open in SpringToolSuite
2. Refresh the Project to fetch and download Dependencies
3. Install pgAdmin and create database and username-password.
4. Goto application.properties file and check port is available, ddl-auto mode to "create" and modify postgres database username and password with your credentials.
5. Now, run the SpringBoot application.
6. copy localhost url from application.properties file and open in browser.
Note: for Swagger interface use : LOCALHOST_URL/swagger-ui.html
7. To run web services in Postman.
    Note : Install Postman and run by using url with method name which you will get in controller classes.
8. Default Credentials for Admin is

    ```
    username: admin@carparking.com
    password: aditya
    ```
    #### Notes - If you are running it for the first time.
    - The project uses port 9117.
    - Also the default port for postgres is used 5432.
    - Change the properties file if requirement arise.

NOTE:  For further Information Refer to presentation.pptx in repository and webservices URL with Json data refer to .config/Tasks.txt File.




### 😊Deployement of CarParkingApplication using Docker-Compose

1.	Create SpringBoot App
2.	Create DockerFile and Add Below Content as per App
3.	Generate Jar File of Application

    a.	Add Dependency into build.gradle File to generate Jar

```java 
task fatJar(type: Jar){
	manifest {
		attributes 'Implementation-Title': 'Gradle Jar File Example',
			'Implementation-Version': version,
			'Main-Class': 'com.cg.CarParkingSytstemApplication'
	}
	baseName = project.name + '-all'
	from { configuration.compile.collect { it.isDirectory() ? it : zipTree(it) } }
	with jar
}
tasks.named('bootJar') {
	launchScript()
}
```

        b. Save and Refresh GradleFile
        c. Goto Project Root Directory and open cmd
        d. Run : gradle build
            Or building Jar without Test Case : 
            gradle build -x test
            
4. Now, Create Github Repository of Spring Boot Application

	Note: Make sure to remove build/ from gitignore file Otherwise your build Jar file wont be uploaded on github.

5. Create EC2 Instance

    a. In Security Group set Type to All Traffic and Source to Anywhere

6. Install/Run Docker using below Command

   ```sh
   sudo yum update -y
   sudo yum install docker -y
   sudo systemctl start docker
   sudo usermod -a -G docker ec2-user
   sudo systemctl enable docker
   sudo systemctl status docker
   docker info : to check it is working or not
   ```

7. Install Git

    ```
    sudo yum install git
    ```

8. Re-login your Session

9. Now Copy Repository code HTTP URL from Github

10. Run Command instance : 
    
    ```
    git clone https://github.com/mossheik/CarParkingApplication.git
    ```

11.	Goto Application Directory Folder

    ```
    cd CarParkingApplication
    ```

12.	Locate and goto Dockerfile

13. Then in CarParkingApplication directory and  Run 
    
    ```
    docker build -t app .
	docker image ls : to check images
    ```

14. Install docker-compose

    ```
    wget https://github.com/docker/compose/releases/download/v2.5.0/docker-compose-linux-x86_64
    
    ls -lrt
    sudo chmod +x docker-compose-linux-x86_64
    ls -lrt
    sudo mv docker-compose-linux-x86_64 docker-compose
    sudo mv docker-compose /usr/local/bin/docker-compose
    docker compose --version

    ```

15. Relogin Session and Create docker-compose.yml file in project root directory

    ```
    vi docker-compose.yml
    ```

    ```java
    version: '3.9'
    services:
    api:
        build: 
        context: .
        ports:
        - "8080:9117"
        depends_on:
        db:
            condition: service_healthy
        environment:
        - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres
        - SPRING_DATASOURCE_USERNAME=postgres
        - SPRING_DATASOURCE_PASSWORD=1
    db:
        image: postgres
        volumes:
        - db_data:/var/lib/postgresql/data
        environment:
        - POSTGRES_PASSWORD=1
        - POSTGRES_USER=postgres
        healthcheck:
        test: ["CMD-SHELL", "pg_isready -U postgres"]
        interval: 10s
        timeout: 5s
        retries: 5
    volumes:
    db_data: {}
    ```

16. Run yaml file

    ```sh
    docker-compose up -d
    docker-compose ps
    ```

17.	Copy Public Address from AWS ec2 instance and view application in browser. Port is 8080.

18.	View Database

    ```sh
    docker exec -it postgrescontainerid
    Su -l postgres
    psql
    ```


### 😊Deployement of CarParkingApplication using Kubernetes

1.	Create EC2 instance with t2.medium, storage 30GB and Security group all traffic anywhere

2.	Install Kubernate 

    ```sh
    curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
    sudo chmod +x kubectl
    sudo mv kubectl /usr/bin/
    sudo su –
    ```

3.	Install minikube

    ```sh
    curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
    sudo install minikube-linux-amd64 /usr/local/bin/minikube
    ```

4.	Install Docker

    ```sh
    sudo yum update -y;sudo yum install docker -y;sudo systemctl start docker;sudo usermod -a -G docker ec2-user;sudo systemctl enable docker;sudo systemctl status docker
    ```

5.	Start minkube
    ```sh
    yum install conntrack
    minikube start --vm-driver=none
    ```

6.	Install git and clone your project repository
    ```sh
    yum install git
    git clone https://github.com/mossheik/CarParkingApplication.git
    cd CarParkingApplication
    ```

7. Build Docker Image and Upload to dockerHub
Signup on dockerhub platform
Come back to project directory

    ```sh
    cd CarParkingApplication
    docker build -t YOURUSERID/carparking .
    docker login #type userid and password
    docker image ls
    docker push mohsin12435/carparking
    ```
    Note : Now, checkout DockerHub.

8. Locate your Deployment files directory
    ```sh
    cd CarParkingApplication
    cd .config
    ```

9.	Apply Deployment files
    ```sh
        kubectl apply -f db_deployment.yml
        kubectl apply -f service_db.yml

        #Copy postgress service ip address by running below command
        kubectl get svc

        #replace jdbc ipaddress in below file with above copied address
        vim app_deployement.yml

        kubectl apply -f app_deployment.yml

        kubectl get pod
        kubectl get pod --show-labels #copy label from here ex: app=server

        vim service_app.yml #paste here in this file at selector: app: server

        kubectl apply -f service_app.yml

        kubectl get all #to check everything which is deployed and now copy port from service/myapp ex: 8080:31650/TCP here port is 31650
    ```

10. Now its time run your application in browser

11. Access Database
    ```sh
            kubectl exec -it postgrespodid bash
            su -l postgres
            psql
    ```
