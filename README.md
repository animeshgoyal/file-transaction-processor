
# file-transaction-processor 
repository has below projects:

- file-handler
- transaction-procssor

# file-handler
File Handler is responsible for:
- Validating the file
- Identifying the file types
- Read file messages
- Redirecting file messages to MQ 

# transaction-procssor
Transaction Processor is responsible for:
- Reading messages from the Queue
- Processing Transactions
- Rule execution to identify downstream systems for message redirection

# Architecture
Architecture has been built to support vertical and horizontal scaling, following technologies have been used to build the system.
- JMS (Active MQ)
- Easy Rules
- LMAX Disruptors
 
