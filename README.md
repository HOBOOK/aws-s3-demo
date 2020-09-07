# AWS-S3-DEMO
### 1. SET UP

<hr/>

#### Backend

##### (1) Configuration
```
ADD application.properties
TO src/main/resources

### MongoDB Config
spring.data.mongodb.database=awsdb
spring.data.mongodb.username=aws
spring.data.mongodb.password=1234
spring.data.mongodb.host={your host}
spring.data.mongodb.port={your port}
spring.data.mongodb.authentication-database=awsdb
server.port=5000

### AWS S3 Config
cloud.aws.credentials.access-key={your access-key}
cloud.aws.credentials.secret-key={your secret-key}
cloud.aws.s3.bucket={your bucket-name}
cloud.aws.region.static=ap-northeast-2(your region of aws)
cloud.aws.stack.auto=false
```

##### (2) Run
```
run src/main/java/com/example/awscloudstorage/AwscloudstorageApplication.java
```
<hr/>

#### Frontend
##### (1) Setup
```
$cd frontend

$npm install
```

##### (2) Run
```
$npm run serve
```
