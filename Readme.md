basically the approach is that we are creating A log interface and a logVisitor interface using the Vistor Approach since it makes sense t
to use that pattern in this case 


So the approach is simple we read the log 
-> we pass the file location as an argument in the command line 
-> the file is parsed and segregated according to it's category 
-> Each paresed Category has a set of sub logics inside so we are creating a new class for it from the log interface 
-> We maintain 3 map variables in the place where we parse the log file is stored and visit it and process it .
-> Then we parse it and generate the result and then it's written into the file using the aggregation classes in the aggregators package .
-> 