##### Local setup
    1. Go to notification/src/main/resources
    1. Run following command to start localstack SQS 
         docker-compose up
    1. Run following command to create queue
         aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name notification_queue
    1. Run following command to list queues
         aws --endpoint-url=http://localhost:4576 sqs list-queues
         
###### Reference
