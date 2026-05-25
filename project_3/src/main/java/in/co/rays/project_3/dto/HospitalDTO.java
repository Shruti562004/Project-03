package in.co.rays.project_3.dto;

public class HospitalDTO extends BaseDTO {
	
	private String  name;
	private String disease;
	private String docName;
	private int bill;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public int getBill() {
		return bill;
	}
	public void setBill(int bill) {
		this.bill = bill;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
