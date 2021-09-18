import sqlite3
from sqlite3.dbapi2 import Cursor
import pandas as pd
from sqlite3 import Error
import csv


def sql_to_csv(connection):
    my_list = []

    cursor1 = connection.cursor()

    query = "SELECT * FROM fault_lines;"

    cursor1.execute(query)
    with open("list_fault_lines.csv", "w") as outfile:
        writer = csv.writer(outfile)
        writer.writerow(col[0] for col in cursor1.description)
        for row in cursor1:
            writer.writerow(row)

    with open("list_fault_lines.csv", "r") as csv_file:
        reader = csv.reader(csv_file)
        
        for i in reader:
            my_list.append(i)

    print(my_list)


def csv_to_sql(con, csv_name):
    csv_data = []
    with open(csv_name, newline='\n') as f:
        reader = csv.reader(f)
        csv_data = list(reader)
    
    cur = con.cursor()

    queryStr = 'CREATE TABLE IF NOT EXISTS t ('
    
    sql_insert_query = 'INSERT INTO t ('
    
     # use your column names here
    
    column_names = csv_data[0]
    
    for i in range(len(column_names)):
        if ' ' in column_names[i]:
            column_names[i] = column_names[i].replace(' ', '')
        if '(' in column_names[i]:
            column_names[i] = column_names[i].replace('(', '').replace(')', '') 
            
        
    second_query = '('
    
    for x in range(len(column_names)):
        
        queryStr += column_names[x]+', '
        sql_insert_query+=column_names[x]+', '
        second_query += '?,'
    
    second_query=second_query[:-1]+')'
    
    queryStr = queryStr[:-2]+')'

        
    sql_insert_query = sql_insert_query[:-2]+') VALUES '
    sql_insert_query+=second_query

    cur.execute(queryStr)
    
    for i in range(1, len(csv_data)):
        list_args = csv_data[i]
        if len(list_args)<6:
            for i in range(6-len(list_args)):
                list_args.append('0')
        cur.execute(sql_insert_query, tuple(list_args))

    
def create_connection(db_file):
    #     """ create a database connection to a SQLite database """
    conn = None
    try:
        #WRITE FUNCTIONS AND THEIR PARAMETERS TO RUN THE CODE
        #conn2 = sqlite3.connect(r"/Users/ayazhan/Desktop/Babel/all_fault_line.db")
        #csv_to_sql(conn2,'/Users/ayazhan/Desktop/Babel/list_volcano.csv')
        #sql_to_csv(conn2)
    except Error as e:
        print(e)
    finally:
        if conn:
            conn.close()


#create_connection(r"/Users/ayazhan/Desktop/Babel/all_fault_line.db")