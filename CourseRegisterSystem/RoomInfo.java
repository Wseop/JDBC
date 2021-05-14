package courseSystem;

public class RoomInfo {
	
	private int room_id;
	private String name;
	private int occupancy;
	
	public RoomInfo(){}	
	public RoomInfo(int room_id, String name, int occupancy) {
		this.room_id = room_id;
		this.name = name;
		this.occupancy = occupancy;
	}

	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}
	public void printRoomInfo(RoomInfo room){
		System.out.printf("%d\t%s\t%d\n", room.getRoom_id(), room.getName(), room.getOccupancy());
	}
}