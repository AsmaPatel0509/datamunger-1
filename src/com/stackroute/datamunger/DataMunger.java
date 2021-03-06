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

		int whereIndex = queryString.indexOf(" where" );
		int groupIndex = queryString.indexOf(" group " );
		int orderIndex = queryString.indexOf(" order " );
		String result = "";
		if(whereIndex != -1) {
			result = queryString.substring(0, whereIndex);
		}else if(groupIndex != -1 && whereIndex == -1){
			result = queryString.substring(0, groupIndex);
		}else if(orderIndex!=-1 && whereIndex == -1 && groupIndex == -1){
			result = queryString.substring(0, orderIndex);
		}
		else {
			result = queryString;
		}
		return result.trim();
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
		
		queryString = queryString.toLowerCase();
		String resultSplit[] = queryString.split(" ");
		
		int pos1 = queryString.indexOf("where");
		if (pos1 == -1) {
			return null;
		}
		String result = "";
		for(int i = 0; i<resultSplit.length; i++) {
			if(resultSplit[i].equals("where")) {
				for(int j=i+1; j<resultSplit.length; j++) {
					if(resultSplit[j].equals("order") || resultSplit[j].equals("group")) {
						break;
					}
					else {
						result = result.concat(" " + resultSplit[j]);
					}
				}
				break;
			}
		}
		
		return result.trim();
		
		
		
		
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
		queryString = queryString.toLowerCase();
		int pos1 = queryString.indexOf("where");
		if (pos1 == -1) {
			return null;
		}
		String [] arr = queryString.split("where")[1].trim().split("group by | order by")[0].trim().split(" and | or ");
		//System.out.println(Arrays.toString(arr));
		return arr;
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
		String[] words=queryString.toLowerCase().split(" ");
		ArrayList<String> s1 = new ArrayList<String>();
		String[] final2=null;
		for(int i=0;i<words.length;i++)
		{
			//System.out.println(words[i]);
			if(words[i].equals("and"))
			{
				s1.add(words[i]);
			}
			if(words[i].equals("or"))
			{
				s1.add(words[i]);
			}
			if(words[i].equals("not"))
			{
				s1.add(words[i]);
			}
		}
		if(s1.size()>0) {
		String final1=String.join(" ", s1);
		 final2=final1.split("[ ]");
		for(int i=0;i<final2.length;i++)
		{
			System.out.println(final2[i]);
		}
		}
		return final2;
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
//		String resultSpace[] = queryString.split(" ");
//		ArrayList<String> list = new ArrayList<String>();
//		
//		int orderIndex = queryString.indexOf(" order ");
//		int groupIndex = queryString.indexOf(" group ");
//		String substr = "";
//		
//		if(groupIndex != -1 && orderIndex!= -1) {
//			substr = queryString.substring(groupIndex + 10, orderIndex);
//			//System.out.println("substr");
//		}
//		if(orderIndex == -1 && groupIndex != -1) {
//			substr = queryString.substring(groupIndex + 10, queryString.length());
//		}
//		if(groupIndex == -1 && orderIndex == -1) {
//			substr = null;
//		}
//		//System.out.println("Query string: " + queryString);
////		System.out.println("Substr group by: " + substr);
//		
//		if(substr.contains(",")){
//			String[] substring = substr.split(",");
//			for(int i =0; i<substr.length(); i++) {
//				list.add(substring[i]);
//			}
//		}else {
//			list.add(substr);
//		}
//		String groupArray[]= new String[list.size()];
//		for(int i=0; i<list.size(); i++) {
//			groupArray[i] = list.get(i).toString();
//		}
//		
//		System.out.println("LISTTTTT: " + list);
//		System.out.println("Group array: " + Arrays.toString(groupArray));
//		
//		return groupArray;
		
		
			String[] words=queryString.toLowerCase().split("[, ]");
			String[] answer=null;
			int i1=0;
			String[] final2=null;
			ArrayList<String> s1 = new ArrayList<String>();
			for(int i=0;i<words.length;i++)
			{
				if(words[i].equals("group"))
				{
					i1=i+2;
				}
			}
			if(i1>0) {
				for(int i=i1;i<words.length;i++)
				{
					//System.out.println(words[i]);
					s1.add(words[i]);
				}
				//		//System.out.println(i1);
				String final1=String.join(" ", s1);
				////		System.out.println(s1);
				 final2=final1.split("[ ]");
				for(int i=0;i<final2.length;i++)
				{
					System.out.println(final2[i]);
				}
			}
			return final2;
		
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
		String arr[]=queryString.toLowerCase().split("[, ]");
		ArrayList<String> ar=new ArrayList();
		String[] final2=null;
		
		for(int i=0;i<arr.length;i++) {
			if(arr[i].contains("sum(")|| arr[i].contains("min(") || arr[i].contains("max(")
					|| arr[i].contains("count(")|| arr[i].contains("avg(")) {
				
				ar.add(arr[i]);
			}
		}
		if(ar.size()>0) {
			String final1=String.join(" ", ar);
			final2=final1.split("[, ]");
			for(int i=0;i<final2.length;i++)
				{
					System.out.println("aggregate functions: " + final2[i]);
				}
			}
		return final2;

	}

}