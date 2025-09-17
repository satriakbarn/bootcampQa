### Project Name
PR API Automation After Office
--------
🚀 How to Run
### 1. Clone the repository:
```bash
git clone https://github.com/satriakbarn/bootcampQa
```

### 2. Install dependencies:
```bash
   mvn clean install
```

### 3. Make sure 'pom.xml' contains :
 ```xml
   <dependencies>
       <!-- Rest Assured -->
       <dependency>
           <groupId>io.rest-assured</groupId>
           <artifactId>rest-assured</artifactId>
           <version>5.3.0</version>
           <scope>test</scope>
       </dependency>

       <!-- TestNG -->
       <dependency>
           <groupId>org.testng</groupId>
           <artifactId>testng</artifactId>
           <version>7.7.0</version>
           <scope>test</scope>
       </dependency>

       <!-- JSON Path (opsional) -->
       <dependency>
           <groupId>io.rest-assured</groupId>
           <artifactId>json-path</artifactId>
           <version>5.3.0</version>
           <scope>test</scope>
       </dependency>
   </dependencies>
   ```

### 4. Run The Test:
```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```
