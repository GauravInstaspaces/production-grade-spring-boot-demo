
# Employee
### API Reference
Provides APIs for **Employees**. Refer to the Swagger documentation for more details.
  
```http
http://localhost:8080/swagger-ui/index.html#/ 
```


### Run Locally

1. Clone the project:
        
    ```bash
    git clone <REPO URL>
    ```
  
2. Navigate to the project directory:
        
    ```bash
    cd employee/
    ```
  
3. Environment set-up: 
        
    ```bash
    export $(cat local.env | sed 's/#.*//g' | xargs) && docker-compose up
    ```
  
4. Install dependencies:
        
    ```bash
    gradle install
    ```
 
5. Start the server:
        
    ```bash
    gradle bootRun
    ```

    

### Running Tests

To run tests, execute the following command:
```bash
gradle test
```


### Deployment
To deploy this project run
    ```bash
    gradle deploy
    ```


### Technology Stack

- Programming Language: JAVA
- Framework: Spring Boot
- Database: MYSQL
- Containerization: Docker, Docker-compose



### Badges


Add badges from somewhere like: [shields.io](https://shields.io/)

- [![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
- [![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)
- [![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](https://www.gnu.org/licenses/agpl-3.0)
