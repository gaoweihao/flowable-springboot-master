package com.flowable.springboot.bean;

/**
 * 报销明细
 * @author Administrator
 *
 */
public class ExpenseDetail extends BaseEntity {

	private static final long serialVersionUID = 7540933892129007372L;

	private String description;
	
	private double sum;
	
	private String type;
	
	private int count;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
