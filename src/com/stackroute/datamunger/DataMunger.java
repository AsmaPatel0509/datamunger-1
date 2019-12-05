package com.stackroute.datamunger;

import java.util.*;

/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class DataMunger {

	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */

	public String[] getSplitStrings(String queryString) {

		String splitString[] = queryString.toLowerCase().split(" ");
		
		System.out.println("Result: " + Arrays.toString(splitString));
		return splitString;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */
	

	public String getFileName(String queryString) {

		String[] fileName = queryString.split(" ");
		//System.out.println("File name array: " + Arrays.toString(fileName));
		String file = "";
		
		for(int i=0; i<fileName.length; i++) {
			if(fileName[i].equals("from"))
				file = fileName[i+1];
		}
		System.out.println("File: " + file);
		return file;
//		return null;
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {

		String[] whereClause = queryString.split("where");
		
		System.out.println("Where: " + Arrays.toString(whereClause));
//		System.out.println("0 " + whereClause[0]);
		return whereClause[0].trim();
//		return null;
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	public String[] getFields(String queryString) {
//
		String[] resultArray = queryString.split("from");
		
		//System.out.println("Fields array from: " + Arrays.toString(resultArray));
		
		String[] resultArr = resultArray[0].split(" ");
//		System.out.println("Fields select: " + Arrays.toString(resultArr));
//		System.out.println(resultArr.length);
		String[] resultField = resultArr[1].split(",");
		
		System.out.println("Fields: " + Arrays.toString(resultField));
		
		//String[] check = resultArray[0].split("[ ,]");
		//System.out.println("Check: " + Arrays.toString(check));
		
		return resultField;
//		return null;
	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {
		
		String[] resultSplit= {};
		if(queryString.contains(" where "))
		{
			resultSplit = queryString.split(" where ");
		}
		System.out.println("resultSplit: "+ Arrays.toString(resultSplit));
		System.out.println("Result where: " + resultSplit[1]);
		
//		String[] resultSpace = resultSplit[1].split(" ");
//		System.out.println("Space separated: " + Arrays.toString(resultSpace));
//		
//		for(int i = 0; i<resultSpace.length; i++) {
//			if(resultSpace[i].equalsIgnoreCase("group") || resultSpace[i].equalsIgnoreCase("order")) {
//				
//			}
//		}
		int index=0;
		String resultString;
		if(resultSplit[1].contains(" group "))
		{
			index = resultSplit[1].indexOf(" group ");
			System.out.println("Index: " + index);
		}
		if(resultSplit[1].contains(" order "))
		{
			index = resultSplit[1].indexOf("order");
			System.out.println("Index: " + index);
		}
		
		if(index!=0)
		{
			resultString = resultSplit[1].substring(0, index-1);
			System.out.println("resultString: " + resultString);

		}
		else
		{
			resultString=resultSplit[1];
			System.out.println("resultString: "+resultString);
		}
		return resultString.toLowerCase();
	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {
		
		String[] resultSplit= {};
		queryString.toLowerCase();
		if(queryString.contains(" where "))
		{
			resultSplit = queryString.split(" where ");
		}
		int index=0;
		String resultString;
		if(resultSplit[1].contains(" group "))
		{
			index = resultSplit[1].indexOf(" group ");
			System.out.println("Index: " + index);
		}
		if(resultSplit[1].contains(" order "))
		{
			index = resultSplit[1].indexOf(" order ");
			System.out.println("Index: " + index);
		}
		
		if(index!=0)
		{
			resultString = resultSplit[1].substring(0, index-1);
			System.out.println("resultString: " + resultString);

		}
		else
		{
			resultString=resultSplit[1];
			System.out.println("resultString: "+resultString);
		}
		
		ArrayList<String> list = new ArrayList<String>();
		
		int andIndex = resultString.indexOf(" and ");
		int orIndex = resultString.indexOf(" or ");
		
		System.out.println("andIndex: "+andIndex);
		System.out.println("orIndex: "+orIndex);
		String substr = "";
		
		if(andIndex != -1 && orIndex != -1) {
			if(andIndex > orIndex) {
				substr = resultString.substring(0, orIndex);
				list.add(substr);
				
				substr = resultString.substring(orIndex + 4, andIndex);
				list.add(substr);
				substr = "";
				
				substr = resultString.substring(andIndex + 5, resultString.length());
				list.add(substr);
				substr = "";
			}else {
				
//				System.out.println(resultString.substring(0,andIndex));
				substr = resultString.substring(0, andIndex);
				list.add(substr);
				System.out.println("substr" + substr);
				substr = "";
				
				substr = resultString.substring(andIndex + 5, orIndex);
				list.add(substr);
				substr = "";
				
				substr = resultString.substring(orIndex + 4, resultString.length());
				list.add(substr);
				substr = "";
			}
		}
		if(andIndex != -1 && orIndex == -1) {
			substr = resultString.substring( 0, andIndex);
			list.add(substr);
			substr = "";
			
			substr = resultString.substring( andIndex+5, resultString.length());
			list.add(substr); 
			substr = "";
		}
		if(orIndex != -1 && andIndex == -1) {
			substr = resultString.substring( 0, orIndex);
			list.add(substr);
			substr = "";
			
			substr = resultString.substring( orIndex + 4, resultString.length());
			list.add(substr); 
			substr = "";
		}
		
		//System.out.println("resultConditions: "+Arrays.toString(resultConditions));
//		Iterator itr=list.iterator(); 
//		
//		while(itr.hasNext()){
//			System.out.println("LIST: ");
//			System.out.println(itr.next());  
//		}  
		String resultConditions[]= new String[list.size()];
		for(int i=0; i<list.size(); i++) {
			resultConditions[i] = list.get(i);
		}
		System.out.println("Result conditions: " + Arrays.toString(resultConditions));
		return resultConditions;
	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public String[] getLogicalOperators(String queryString) {
		
		String[] resultSpace = queryString.split(" ");
		String[] resultLogicalOperator = {};
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i =0; i<resultSpace.length; i++) {
			if(resultSpace[i].equals("and") || resultSpace[i].equals("or")) {
				list.add(resultSpace[i]);
			}
		}
		
//		System.out.println("Query string: " + queryString);
//		System.out.println("Result space: " + Arrays.toString(resultSpace));
//		System.out.println("Result list: " + list);
		
		String logicalArray[]= new String[list.size()];
		for(int i=0; i<list.size(); i++) {
			logicalArray[i] = list.get(i);
		}
		
		return logicalArray;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString) {

		String resultSpace[] = queryString.split(" ");
		ArrayList<String> list = new ArrayList<String>();
		
		int orderIndex = queryString.indexOf(" order ");
		String substr = "";
		System.out.println("Order index: " + orderIndex);
		if(orderIndex!= -1) {
			substr = queryString.substring(orderIndex + 10, queryString.length());
			//System.out.println("substr");
		}
		
		System.out.println("Query string: " + queryString);
		System.out.println("Substr order by: " + substr);
		
		if(substr.contains(",")){
			String[] substring = substr.split(",");
			for(int i =0; i<substr.length(); i++) {
				list.add(substring[i]);
			}
		}else {
			list.add(substr);
		}
		String orderArray[]= new String[list.size()];
		for(int i=0; i<list.size(); i++) {
			orderArray[i] = list.get(i);
		}
		
		return orderArray;
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */

	public String[] getGroupByFields(String queryString) {
		String resultSpace[] = queryString.split(" ");
		ArrayList<String> list = new ArrayList<String>();
		
		int orderIndex = queryString.indexOf(" order ");
		int groupIndex = queryString.indexOf(" group ");
		String substr = "";
		
		if(groupIndex != -1 && orderIndex!= -1) {
			substr = queryString.substring(groupIndex + 10, orderIndex);
			//System.out.println("substr");
		}
		if(orderIndex == -1 && groupIndex != -1) {
			substr = queryString.substring(groupIndex + 10, queryString.length());
		}
		//System.out.println("Query string: " + queryString);
//		System.out.println("Substr group by: " + substr);
		
		if(substr.contains(",")){
			String[] substring = substr.split(",");
			for(int i =0; i<substr.length(); i++) {
				list.add(substring[i]);
			}
		}else {
			list.add(substr);
		}
		String groupArray[]= new String[list.size()];
		for(int i=0; i<list.size(); i++) {
			groupArray[i] = list.get(i);
		}
		return groupArray;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */

	public String[] getAggregateFunctions(String queryString) {

		System.out.println("Aggregate query string : " + queryString);
		String space[]=queryString.split(" ");
//		System.out.println("Space: "+Arrays.toString(space));
		String comma[]=space[1].split(",");
		System.out.println("Comma: "+Arrays.toString(comma));
		
		return comma;
	}

}