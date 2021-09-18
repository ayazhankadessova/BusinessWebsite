# my-ds-babel

######################csv_to_sql:#################

Two parameters: connection to a database which you are going to write in, and a csv-file you're going to read in;

To make a database, we need two SQL queries: 
    1. CREATE TABLE t(param1, param2, param3, ...). 
    2. INSERT INTO t (param1, param2, param3) VALUES (?, ?, ?) and the things we are going to insert in. You can see that depending on the size of the column names, dynamic amount of '?' and param's is going to be created.

Another comment: If insufficient data, INSERT "0" inside of the list-row.


#######################sql_to_csv#################

Ayazhan did that one - uses cursor and iteratively reads file, then writes into list_fault_lines.csv

returns list if needed - that one needs some work, it does not work on my machine, yet works on hers. Very strange indeed. Too bad!

#######################Testing#################### CSV TO SQL
Create a database inside of the folder you are working in and copy its' path:

conn = sqlite.connect('database.path') or something like this i dont remember

csv_to_sql(conn, 'list_volcano.csv') - THIS WRITES list_volcano.csv INTO DATABASE WHICH YOU HAVE CREATED. CONGRATULATIONS. NOW YOU CAN TEST SQL_TO_CSV METHOD:



#######################Testing#################### SQL TO CSV

conn = sqlite.connect(r'all_fault_line.db')

sql_to_csv(conn)