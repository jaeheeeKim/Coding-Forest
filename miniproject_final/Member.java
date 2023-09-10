package miniproject_final;

import java.io.*;

public class Member implements Serializable{
	private String login;	//접속 - 1, 미접속 - 0
	private String chara;	//여 - 2, 남 - 1
	private String id;		//varchar(20)
	private String pw;		//varchar(20)
	private String name;	//varchar(20)
	private String mbti;	//varchar(20)
	private String hobby;	//varchar(100)
	private String food;	//varchar(100)
	private String color;	//varchar(100)
	private String memo1;	//varchar(300)
	private String memo2;	//varchar(300)
	private String memo3;	//varchar(300)
	private String memo4;	//varchar(300)
	private String memo5;	//varchar(300)
	
	//id 기준(=닉네임)
	//pw 분실 시, id 로 찾기
	//id 분실 시, name(실명) 으로 찾기
	
	public Member(String login, String chara, String id, String pw, String name, String mbti, String hobby, String food, String color, String memo1, String memo2, String memo3, String memo4, String memo5) {
		this.login=login;
		this.chara=chara;
		this.id = id;
		this.pw =pw;
		this.name = name;
		this.mbti = mbti;
		this.hobby = hobby;
		this.food = food;
		this.color = color;
		this.memo1 = memo1;
		this.memo2 = memo2;
		this.memo3 = memo3;
		this.memo4 = memo4;
		this.memo5 = memo5;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login=login;
	}
	public String getChara() {
		return chara;
	}
	public String getID() {
		return id;
	}
	public String getPW() {
		return pw;
	}
	public void setPW(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public String getMbti() {
		return mbti;
	}
	public void setMbti(String mbti) {
		this.mbti = mbti;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food =food;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getMemo1() {
		return memo1;
	}
	
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	
	public String getMemo2() {
		return memo2;
	}
	
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	
	public String getMemo3() {
		return memo3;
	}
	
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}
	
	public String getMemo4() {
		return memo4;
	}
	
	public void setMemo4(String memo4) {
		this.memo4 = memo4;
	}
	
	public String getMemo5() {
		return memo5;
	}
	
	public void setMemo5(String memo5) {
		this.memo5 = memo5;
	}
	@Override
	public String toString() {
		return this.login+"/"+this.chara+"/"+this.id+"/"+this.pw+"/"+this.name+"/"+this.mbti+"/"+this.hobby+"/"+this.food+"/"+this.color+"/"+this.memo1+"/"+this.memo2+"/"+this.memo3+"/"+this.memo4+"/"+this.memo5;
	}
}