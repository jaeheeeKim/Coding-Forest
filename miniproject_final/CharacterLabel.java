package miniproject_final;

import javax.swing.*;
import java.awt.*;

// 이미지 사이즈: 60*75
// 패널 전체 사이즈: 60*95
public class CharacterLabel extends JPanel{
	public ImageIcon img_default;
	
	public ImageIcon img;	// 현재이미지
	
	private JLabel nick;
	public JLabel chara;
	
	public String nickName;
	public int charaNum;
	private int x;
	private int y;
	
	//생성자 (캐릭터 번호를 매개변수로 받아서 생성)
	public CharacterLabel(String name, int charaNum, int x, int y){
		this.charaNum = charaNum;
		this.nickName = name;
		this.x = x;
		this.y = y;
		
		imgSetting(charaNum); //캐릭터 이미지 선택
		this.nick = new JLabel(name, JLabel.CENTER);
		chara = new JLabel(img);
		
		this.setLayout(null);
		this.add(this.nick);
		this.nick.setLocation(0, 0);
		this.add(chara);
		chara.setLocation(0, 20);
		
		nick.setSize(60, 20);
		chara.setSize(60, 75);
		this.setSize(60, 95);
		
		this.setBackground(new Color(255, 0, 0, 0));
		
	}
	
	private void imgSetting(int num) {
		//1번 캐릭터
		if(num==1) {
			img_default = setImageIconSize(new ImageIcon("img/main/pants.png"));
			img = img_default;
		}
		//2번 캐릭터
		if(num==2){
			img_default = setImageIconSize(new ImageIcon("img/main/dress.png"));
			img = img_default;
		}
	}
	
	//이미지 사이즈 조절
	private ImageIcon setImageIconSize(ImageIcon ii) {
		Image img = ii.getImage().getScaledInstance(60, 75, Image.SCALE_FAST);
		return new ImageIcon(img);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCharaNum() {
		return charaNum;
	}

}
