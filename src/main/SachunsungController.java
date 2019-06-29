package main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SachunsungController {
	private List<ImageView> images = new ArrayList<ImageView>(); 
	private List<Integer> imgnameList = new ArrayList<Integer>();
	private ImageView imgD;
	private int clickedImg = 0;
	private int chk=-1;
	private  HashSet<Integer> nSet = new HashSet<Integer>();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gPane;

    @FXML
    void initialize() {
        assert gPane != null : "fx:id=\"gPane\" was not injected: check your FXML file 'sachunsung.fxml'.";
        
        for(int i=0;i<8;i++) {
        	for(int j=0;j<10;j++) {
        		
        		ImageView imgView= new ImageView();
        		images.add(imgView);
        		gPane.add(imgView, j, i);
        		
        	}
        }
        
        
        HashSet<Integer> number = new HashSet<Integer>();
        
        for(int i=0;number.size()<80;) {
        	
        		int numb = (int)(Math.random()*80);
        		if(number.add(numb)){//set 에 추가가 되면 중복된 수가 아님..
        			if(numb<20) {
        				//그림 이름을 숫자로 해놓았으므로 랜덤수 에 해당하는 그림을 i 번째 imgView에 setting 해준다.
        				File file = new File("img/"+numb+".jpg");
        				Image image = new Image(file.toURI().toString(),63, 77, false, false);
        				images.get(i).setImage(image);
        				
        				// seeting 된 그림을 저장할 list 를 만들고 그림 번호(랜덤수->numb)를 setting 한다.
        				imgnameList.add(numb);
          			  
        			}else if(numb<40) {
        				
        				  File file = new File("img/"+(numb-20)+".jpg");
            			  Image image = new Image(file.toURI().toString(),63, 77, false, false);
            			  images.get(i).setImage(image);
            			  
            			// seeting 된 그림을 저장할 list에 그림 번호(랜덤수->numb)를 seeting 한다.
            			 //imgnameList 에는 같은 그림번호가 두개씩 있다. 
            			  imgnameList.add(numb-20);
          				
        			}else if(numb<60) {
        				
      				  File file = new File("img/"+(numb-40)+".jpg");
          			  Image image = new Image(file.toURI().toString(),63, 77, false, false);
          			  images.get(i).setImage(image);
          			  imgnameList.add(numb-40);
        				
        				
        			}else {
        				 File file = new File("img/"+(numb-60)+".jpg");
             			  Image image = new Image(file.toURI().toString(),63, 77, false, false);
             			  images.get(i).setImage(image);
             			  imgnameList.add(numb-60);
        				
        			}
        			
        			  
        			i++;
        			
        			
        		}
        		
        }
        
        
        for(ImageView img :images ) {
        	img.setOnMouseClicked(e->{
        		
        		ImageView imgV = (ImageView) e.getSource();
        		
        		
        		 if(clickedImg==0) {
        			 imgD = imgV;
        				 clickedImg++;
        			 
        		 }else {
        			 //같은 그림을 두번 클릭하면 clickedImg를 0으로 만들어주고 return한다.
        			 if(imgD ==imgV) {
        				 clickedImg=0;
        				 return;
        			 }
        			 
        			 int vgetName = images.indexOf(imgV);
	     			 int dgetName=images.indexOf(imgD);
	     			 
	     			 
	     			if(imgnameList.get(vgetName)==imgnameList.get(dgetName)) {
	     			
	     				if(checkSide(vgetName,dgetName)) {
	     					imgV.setVisible(false);
		        			 imgD.setVisible(false);
		        			 chk=-1;
		        			 clickedImg=0;
		        			 nSet.clear();
		        			 return;
		        			 
	     				}else if(searchRoad(vgetName,dgetName,5)) {
	     					imgV.setVisible(false);
		        			 imgD.setVisible(false);
		        			 chk=-1;
		        			 clickedImg=0;
		        			 nSet.clear();
		        			 return;
	     				}
	     				
//	     				PointVO vpot= changePoint(vgetName);	
//	     				PointVO dpot= changePoint(dgetName);	
//	     				
//	     				System.out.println("Vindex :"+vgetName);
//	     				System.out.println("Dindex :"+dgetName);
//	     				System.out.println("V:("+vpot.getX()+","+vpot.getY()+")");
//	     				System.out.println("D:("+dpot.getX()+","+dpot.getY()+")");
//	     				
//	     				Map udlr = getUDLRidx(dgetName);
//	     				
//	     				System.out.println("UP:"+(int)udlr.get("UP"));
//	     				System.out.println("Down:"+(int)udlr.get("Down"));
//	     				System.out.println("Left:"+(int)udlr.get("Left"));
//	     				System.out.println("Rigth:"+(int)udlr.get("Right"));
	     				
	        			 
	        			 
	     			}
	     			nSet.clear();
	     			 clickedImg=0;
	     			 
        		 }
        		
        		
        	});
        }
        
        
    }//initialize
    
    //index를 좌표로 변환해주는 함수
    public PointVO changePoint (int index) {
    	PointVO pot = new PointVO();
    	pot.setX(index/10);
    	pot.setY(index%10);
    	
    	return pot;
    	
    }
    
    
    //좌표를 index 로 변환해주는 함수
    public int changeIndex(PointVO pot) {
    	
    	int idx = pot.getX()*10+pot.getY();
    	return idx;
    	
    }
    
    //index에 해당하는 곳의 image가 visible 인지를 판단하는 함수
    public boolean visibleImg(int i) {
    	
    	return images.get(i).isVisible();
    }
    
    
    //index를 넣으면 상하좌우 index를 구하는 함수
    public Map<String,Integer> getUDLRidx(int i) {
    	HashMap<String,Integer> idxMap = new HashMap<String, Integer>();
    	
    	//상
    	PointVO potUP = changePoint(i);
    	if(!(potUP.getX()==0)) {
    		potUP.setX(potUP.getX()-1);
        	idxMap.put("UP",changeIndex(potUP));
    	}else {
    		idxMap.put("UP",100);
    	}
    	
    	
    	//하
    	PointVO potDown = changePoint(i);
    	
    	if(!(potDown.getX()==7)) {
	    	potDown.setX(potDown.getX()+1);
	    	idxMap.put("Down",changeIndex(potDown));
    	}else {
    		idxMap.put("Down",100);
    	}
    	
    	
    	//좌
    	PointVO potLeft = changePoint(i);
    	if(!(potDown.getY()==0)) {
    		potLeft.setY(potLeft.getY()-1);
        	idxMap.put("Left",changeIndex(potLeft));
    	}else {
    		idxMap.put("Left",100);
    	}
    	
    	//우
    	
    	PointVO potRight = changePoint(i);
    	if(!(potDown.getY()==9)) {
    		potRight.setY(potRight.getY()+1);
        	idxMap.put("Right",changeIndex(potRight));
    	}else {
    		idxMap.put("Right",100);
    	}
    	
    	
    	return idxMap;
    }
    
    
    public boolean checkSide(int v, int d) {
    	PointVO vPot = changePoint (v);
    	PointVO dPot = changePoint (d);
    	
    	if(vPot.getX()==0&&dPot.getX()==0) {
    		return true;
    	}else if(vPot.getX()==7&&dPot.getX()==7){
    		return true;
    	}else if(vPot.getY()==0&&dPot.getY()==0) {
    		return true;
    	}else if(vPot.getY()==9&&dPot.getY()==9) {
    		return true;
    	}
    	
    	return false;
    }
    
    
    public boolean searchRoad(int v, int d, int index) {
    	if(v==100) {
    		return false;
    	}

    	if(!(nSet.add(v))){
    		return false;
    	}
    	
    	List<Integer> udlrList = new ArrayList<Integer>();
    	
    	Map<String,Integer> udlrMap = getUDLRidx(v);
    	udlrList.add((int)udlrMap.get("UP"));
    	udlrList.add((int)udlrMap.get("Down"));
    	udlrList.add((int)udlrMap.get("Left"));
    	udlrList.add((int)udlrMap.get("Right"));
    	
    	for(int i =0;i<udlrList.size();i++) {
    		if(udlrList.get(i)==d) {
    			return true;
    		}
    		
    	}
    	if(udlrList.get(0)==100&&udlrList.get(1)==100&&udlrList.get(2)==100&&udlrList.get(3)==100) {
    		
        	return false;
        	
        }
    	
    	for(int i=0;i<udlrList.size();i++) {
	    	
    		if(udlrList.get(i)==100) {
    			continue;
    		}else {
    			if(visibleImg(udlrList.get(i))) {
    				udlrList.set(i, 100);
    			}
    		}
    	}
    	
    	
    	for(int i=0; i<udlrList.size();i++) {
    		
    		if(udlrList.get(i)==100) {
    			continue;
    		}else {
    			if(index!=i) {
					chk++;
					if(chk>3) {
						nSet.add(udlrList.get(i)); 
						udlrList.set(i, 100);
						chk=0;
						return false;
					}
					
    			}
    			
    			if(searchRoad(udlrList.get(i),d,i)) {
    				return true;
    			}else {
//    				nSet.add(udlrList.get(i)); 
    				udlrList.set(i, 100);
    				continue;
    			}
    			
    		}
    	}
    	
    	return false;
    	
    }	
    
}
