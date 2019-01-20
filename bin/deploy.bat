cd ..
cd myshop-dependencies
call mvn deploy

cd ..
cd myshop-commons
call mvn deploy

cd ..
cd myshop-commons-domain
call mvn deploy

cd ..
cd myshop-commons-mapper
call mvn deploy

cd ..
cd myshop-commons-dubbo
call mvn deploy

cd ..
cd myshop-static-backend
call mvn deploy

cd ..
cd myshop-service-user-api
call mvn deploy

cd ..
cd myshop-service-content-api
call mvn deploy

cd ..
cd myshop-service-redis-api
call mvn clean