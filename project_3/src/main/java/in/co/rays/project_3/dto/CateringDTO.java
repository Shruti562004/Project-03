package in.co.rays.project_3.dto;

public class CateringDTO extends BaseDTO {
	
	private String vendorName;
	private String menuType;
	private int cost;
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return menuType;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return menuType;
	}
	

}
