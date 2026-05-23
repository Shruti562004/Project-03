package in.co.rays.project_3.dto;

public class InventoryDTO  extends BaseDTO{
	
	private String name;
	
	private int stock;
	private String supplier;
	private long price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return supplier;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return supplier;
	}
	

}
