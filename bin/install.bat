cd ..
cd myshop-dependencies
call mvn install

cd ..
cd myshop-commons
call mvn install

cd ..
cd myshop-commons-domain
call mvn install

cd ..
cd myshop-commons-mapper
call mvn install

cd ..
cd myshop-commons-dubbo
call mvn install

cd ..
cd myshop-static-backend
call mvn install

cd ..
cd myshop-service-user-api
call mvn install

cd ..
cd myshop-service-content-api
call mvn install

cd ..
cd myshop-service-redis-api
call mvn clean