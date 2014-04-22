package pathfind;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CarletonMap extends MapSystem{
	
	LinkedHashMap<String,Node> buildingList;
	
	public LinkedHashMap<String,Node> getBuildingList(){
		return buildingList;
	}

	public CarletonMap(){
		super();
		
		//Missing, Tennis Centre, Parking Garage
		
		//Initializing buildings at Carleton
		Node v44 = new Node("O-Train Station (To Bayview)",2106,3603,"O2","N/A",true);
		Node v43 = new Node("O-Train Station (To Greenboro)",2037,3602,"O1","N/A",true);
		Node v42 = new Node("Visualization and Simulation Building",478,6304,"VS","N/A",true);
		Node v41 = new Node("University Centre",1447,4768,"UC","EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE",true);
		Node v40 = new Node("Tory Building",1275,5115,"TB","N/A",true);
		Node v39 = new Node("Stormont Dundas House",1455,2430,"SD","N/A",true);
		Node v38 = new Node("Steacie Building",1760,5097,"SC","N/A",true);
		Node v37 = new Node("Southam Hall",716,5889,"SA","N/A",true);
		Node v36 = new Node("Social Sciences Research Building",575,6355,"SR","N/A",true);
		Node v35 = new Node("St. Patrick’s Building",1278,2577,"SP","N/A",true);
		Node v34 = new Node("Russell House",1138,2882,"RU","N/A",true);
		Node v33 = new Node("Robertson Hall",2700,4762,"RO","N/A",true);
		Node v32 = new Node("Residence Commons",1612,2851,"CO","N/A",true);
		Node v31 = new Node("Renfrew House",1617,3315,"RH","N/A",true);
		Node v30 = new Node("River Building",2041,5302,"RB","N/A",true);
		Node v29 = new Node("Prescott House",1705,3533,"PH","N/A",true);
		Node v28 = new Node("Paterson Hall",1139,5617,"PA","N/A",true);
		Node v27 = new Node("National Wildlife Research Building",3348,4378,"NW","N/A",true);
		Node v26 = new Node("Nesbitt Biology Building",3103,4459,"NB","N/A",true);
		Node v25 = new Node("Mackenzie Building",1522,4056,"ME","N/A",true);
		Node v24 = new Node("Minto Centre for Advanced Studies in Engineering (CASE)",1734,3801,"MC","N/A",true);
		Node v23 = new Node("Maintenance Building",2464,4068,"MB","N/A",true);
		Node v22 = new Node("MacOdrum Library",811,5462,"ML","N/A",true);
		Node v21 = new Node("Lennox and Addington House",1553,3191,"LX","N/A",true);
		Node v20 = new Node("Loeb Building",1000,5973,"LA","N/A",true);
		Node v19 = new Node("Life Sciences Research Building",1320,5893,"LS","N/A",true);
		Node v18 = new Node("Leeds House",1128,2309,"LE","N/A",true);
		Node v17 = new Node("Lanark House",1347,3505,"LH","N/A",true);
		Node v16 = new Node("Human Computer Interaction Building (HCI)",682,6242,"HC","N/A",true);
		Node v15 = new Node("Herzberg Laboratories",1600,5404,"HP","N/A",true);
		Node v14 = new Node("Gymnasium",3068,3547,"GY","N/A",true);
		Node v13 = new Node("Grenville House",1139,3150,"GR","N/A",true);
		Node v12 = new Node("Glengarry House",1646,3000,"GH","N/A",true);
		Node v11 = new Node("Frontenac House",1327,3363,"FR","N/A",true);
		Node v10 = new Node("Fieldhouse",2615,2907,"FH","N/A",true);
		Node v9 = new Node("Dunton Tower",861,5218,"DT","N/A",true);
		Node v8 = new Node("Colonel By Child Care Centre",2754,3658,"CC","N/A",true);
		Node v7 = new Node("Canal Building",1176,4351,"CB","N/A",true);
		Node v6 = new Node("Carleton Technology & Training Centre (CTTC)",2945,4181,"TT","N/A",true);
		Node v5 = new Node("Carleton Ice House",3278,3587,"IH","N/A",true);
		Node v4 = new Node("Azrieli Theatre",1072,4818,"AT","N/A",true);
		Node v3 = new Node("Azrieli Pavilion",997,5020,"AP","N/A",true);
		Node v2 = new Node("Athletics",2895,3246,"AC","N/A",true);
		Node v1 = new Node("Architecture Building",1537,4426,"AA","N/A",true);
		Node v0 = new Node("Alumni Hall",3090,3144,"AH","N/A",true);
		
		
		//Adding all the vertexes that are for the actual walking paths at carleton
		Node p1 = new Node("N/A",755,6508,"N/A","N/A",false);
		Node p2 = new Node("N/A",1641,5363,"N/A","N/A",false);
		Node p3 = new Node("N/A",1459,5134,"N/A","N/A",false);
		Node p4 = new Node("N/A",1419,5277,"N/A","N/A",false);
		Node p5 = new Node("N/A",1536,5392,"N/A","N/A",false);
		Node p6 = new Node("N/A",1680,4808,"N/A","N/A",false);
		Node p7 = new Node("N/A",1558,4620,"N/A","N/A",false);
		
		Node p8 = new Node("N/A",1169,4960,"N/A","N/A",false);
		Node p9 = new Node("N/A",1900,5003,"N/A","N/A",false);
		Node p10 = new Node("N/A",2118,5257,"N/A","N/A",false);
		Node p11 = new Node("N/A",1416,4639,"N/A","N/A",false);
		Node p12 = new Node("N/A",1291,4444,"N/A","N/A",false);
		Node p13 = new Node("N/A",1452,4347,"N/A","N/A",false);
		Node p14 = new Node("N/A",1577,4195,"N/A","N/A",false);
		Node p15 = new Node("N/A",1070,4554,"N/A","N/A",false);
		Node p16 = new Node("N/A",943,4721,"N/A","N/A",false);
		Node p17 = new Node("N/A",915,4840,"N/A","N/A",false);
		Node p18 = new Node("N/A",1059,5010,"N/A","N/A",false);
		Node p19 = new Node("N/A",1178,5200,"N/A","N/A",false);
		Node p20 = new Node("N/A",1868,4107,"N/A","N/A",false);
		Node p21 = new Node("N/A",1269,4578,"N/A","N/A",false);
		Node p22 = new Node("N/A",1357,4551,"N/A","N/A",false);
		Node p23 = new Node("N/A",1880,4420,"N/A","N/A",false);
		Node p24 = new Node("N/A",1867,3739,"N/A","N/A",false);
		Node p25 = new Node("N/A",1564,3887,"N/A","N/A",false);
		Node p26 = new Node("N/A",1434,3662,"N/A","N/A",false);
		Node p27 = new Node("N/A",1440,3533,"N/A","N/A",false);
		Node p28 = new Node("N/A",1982,4930,"N/A","N/A",false);
		Node p29 = new Node("N/A",2052,5105,"N/A","N/A",false);
		Node p30 = new Node("N/A",1332,5394,"N/A","N/A",false);
		Node p31 = new Node("N/A",1252,5313,"N/A","N/A",false);
		Node p32 = new Node("N/A",1288,5557,"N/A","N/A",false);
		Node p33 = new Node("N/A",1049,5455,"N/A","N/A",false);
		Node p34 = new Node("N/A",936,5529,"N/A","N/A",false);
		Node p35 = new Node("N/A",832,5369,"N/A","N/A",false);
		Node p36 = new Node("N/A",830,5634,"N/A","N/A",false);
		Node p37 = new Node("N/A",695,5743,"N/A","N/A",false);
		Node p38 = new Node("N/A",886,5935,"N/A","N/A",false);
		Node p39 = new Node("N/A",998,5856,"N/A","N/A",false);
		Node p40 = new Node("N/A",1177,5874,"N/A","N/A",false);
		Node p41 = new Node("N/A",1310,5781,"N/A","N/A",false);
		Node p42 = new Node("N/A",779,5298,"N/A","N/A",false);
		Node p43 = new Node("N/A",885,5240,"N/A","N/A",false);
		Node p44 = new Node("N/A",1100,5091,"N/A","N/A",false);
		Node p45 = new Node("N/A",1439,3376,"N/A","N/A",false);
		Node p46 = new Node("N/A",1350,3196,"N/A","N/A",false);
		Node p47 = new Node("N/A",1347,2936,"N/A","N/A",false);
		Node p48 = new Node("N/A",1168,2944,"N/A","N/A",false);
		Node p49 = new Node("N/A",1615,2934,"N/A","N/A",false);
		Node p50 = new Node("N/A",1749,2928,"N/A","N/A",false);
		Node p51 = new Node("N/A",1880,2935,"N/A","N/A",false);
		Node p52 = new Node("N/A",1378,2596,"N/A","N/A",false);
		Node p53 = new Node("N/A",1360,2338,"N/A","N/A",false);
		Node p54 = new Node("N/A",2314,5032,"N/A","N/A",false);
		Node p55 = new Node("N/A",2539,4810,"N/A","N/A",false);
		Node p56 = new Node("N/A",2814,4676,"N/A","N/A",false);
		Node p57 = new Node("N/A",2735,4480,"N/A","N/A",false);
		Node p58 = new Node("N/A",2823,4453,"N/A","N/A",false);
		Node p59 = new Node("N/A",2742,4276,"N/A","N/A",false);
		Node p60 = new Node("N/A",2549,3831,"N/A","N/A",false);
		Node p61 = new Node("N/A",2896,3741,"N/A","N/A",false);
		Node p62 = new Node("N/A",2613,3600,"N/A","N/A",false);
		Node p63 = new Node("N/A",2852,3447,"N/A","N/A",false);
		Node p64 = new Node("N/A",2429,3537,"N/A","N/A",false);
		Node p65 = new Node("N/A",3005,3381,"N/A","N/A",false);
		Node p66 = new Node("N/A",2959,3252,"N/A","N/A",false);
		Node p67 = new Node("N/A",2806,3190,"N/A","N/A",false);
		Node p68 = new Node("N/A",2893,4655,"N/A","N/A",false);
		Node p69 = new Node("N/A",3274,4250,"N/A","N/A",false);
		Node p70 = new Node("N/A",2242,3938,"N/A","N/A",false);
		Node p71 = new Node("N/A",1968,4058,"N/A","N/A",false);
		Node p72 = new Node("N/A",1953,3712,"N/A","N/A",false);
		Node p73 = new Node("N/A",2150,3614,"N/A","N/A",false);
		Node p74 = new Node("N/A",1865,3658,"N/A","N/A",false);
		Node p75 = new Node("N/A",1951,3667,"N/A","N/A",false);
		Node p76 = new Node("N/A",2551,4028,"N/A","N/A",false);
		Node p77 = new Node("N/A",2480,3854,"N/A","N/A",false);
		
		//Linking all of the vertexes to other vertex's that can be access from it
		addBidirectionalEdge(p1, v36);
		addBidirectionalEdge(p2, v15);
		addBidirectionalEdge(p3, p2);
		addBidirectionalEdge(p3, p4);
		addBidirectionalEdge(p4, p5);
		addBidirectionalEdge(v15, p5);
		addBidirectionalEdge(p4, v40);
		addBidirectionalEdge(p3, p6);
		addBidirectionalEdge(p7, p6);
		addBidirectionalEdge(p7, v41);
		addBidirectionalEdge(p8, v40);
		addBidirectionalEdge(p8, v41);
		addBidirectionalEdge(p8, v4);
		addBidirectionalEdge(p9, p6);
		addBidirectionalEdge(p9, v38);
		addBidirectionalEdge(p2, v38);
		addBidirectionalEdge(p10, p9);
		addBidirectionalEdge(p10, v30);
		addBidirectionalEdge(p7, v1);
		addBidirectionalEdge(p7, p11);
		addBidirectionalEdge(p11, p12);
		addBidirectionalEdge(v7, p12);
		addBidirectionalEdge(p13, p12);
		addBidirectionalEdge(p13, v1);
		addBidirectionalEdge(p13, p14);
		addBidirectionalEdge(p14, v25);
		addBidirectionalEdge(p12, p15);
		addBidirectionalEdge(p15, p3);
		addBidirectionalEdge(p15, p16);
		addBidirectionalEdge(p16, v4);
		addBidirectionalEdge(p16, p17);
		addBidirectionalEdge(p17, p18);
		addBidirectionalEdge(v3, p18);
		addBidirectionalEdge(p18, p8);
		addBidirectionalEdge(p19, v40);
		addBidirectionalEdge(p14, p20);
		addBidirectionalEdge(p3, v41);
		addBidirectionalEdge(p21, v41);
		addBidirectionalEdge(p12, p21);
		addBidirectionalEdge(p12, p22);
		addBidirectionalEdge(p22, p11);
		addBidirectionalEdge(p22, p21);
		addBidirectionalEdge(p22, v1);
		addBidirectionalEdge(p23, p7);
		addBidirectionalEdge(p23, p20);
		addBidirectionalEdge(p24, p20);
		addBidirectionalEdge(p24, v24);
		addBidirectionalEdge(p25, v24);
		addBidirectionalEdge(p25, v25);
		addBidirectionalEdge(p25, p26);
		addBidirectionalEdge(p27, p26);
		addBidirectionalEdge(p27, v17);
		addBidirectionalEdge(p27, v29);
		addBidirectionalEdge(p23, p28);
		addBidirectionalEdge(p28, p9);
		addBidirectionalEdge(p28, p29);
		addBidirectionalEdge(p10, p29);
		addBidirectionalEdge(p4, p30);
		addBidirectionalEdge(p31, p30);
		addBidirectionalEdge(p31, p19);
		addBidirectionalEdge(p30, p32);
		addBidirectionalEdge(v28, p32);
		addBidirectionalEdge(p31, p33);
		addBidirectionalEdge(v28, p33);
		addBidirectionalEdge(p34, p33);
		addBidirectionalEdge(p34, p35);
		addBidirectionalEdge(v22, p35);
		addBidirectionalEdge(p36, p35);
		addBidirectionalEdge(p36, v22);
		addBidirectionalEdge(p36, p37);
		addBidirectionalEdge(p37, v37);
		addBidirectionalEdge(p38, v37);
		addBidirectionalEdge(p38, p39);
		addBidirectionalEdge(p36, p39);
		addBidirectionalEdge(v20, p39);
		addBidirectionalEdge(p40, p39);
		addBidirectionalEdge(p40, p41);
		addBidirectionalEdge(p32, p41);
		addBidirectionalEdge(p35, p42);
		addBidirectionalEdge(p43, p42);
		addBidirectionalEdge(p43, v9);
		addBidirectionalEdge(p43, p44);
		addBidirectionalEdge(p19, p44);
		addBidirectionalEdge(p18, p44);
		addBidirectionalEdge(p27, p45);
		addBidirectionalEdge(v11, p45);
		addBidirectionalEdge(v31, p45);
		addBidirectionalEdge(p45, p46);
		addBidirectionalEdge(v21, p46);
		addBidirectionalEdge(p46, p47);
		addBidirectionalEdge(p48, p47);
		addBidirectionalEdge(p48, v13);
		addBidirectionalEdge(p48, v34);
		addBidirectionalEdge(p47, p49);
		addBidirectionalEdge(v32, p49);
		addBidirectionalEdge(p49, p50);
		addBidirectionalEdge(p51, p50);
		addBidirectionalEdge(p47, p52);
		addBidirectionalEdge(v35, p52);
		addBidirectionalEdge(v39, p52);
		addBidirectionalEdge(p53, p52);
		addBidirectionalEdge(p53, v18);
		addBidirectionalEdge(p54, p29);
		addBidirectionalEdge(p54, p55);
		addBidirectionalEdge(v33, p55);
		addBidirectionalEdge(v33, p56);
		addBidirectionalEdge(p57, p56);
		addBidirectionalEdge(p57, p58);
		addBidirectionalEdge(p59, p58);
		addBidirectionalEdge(p59, v6);
		addBidirectionalEdge(p59, p60);
		addBidirectionalEdge(p61, p60);
		addBidirectionalEdge(v8, p61);
		addBidirectionalEdge(v5, p61);
		addBidirectionalEdge(p60, p62);
		addBidirectionalEdge(p63, p62);
		addBidirectionalEdge(p63, p64);
		addBidirectionalEdge(p60, p64);
		addBidirectionalEdge(p63, p65);
		addBidirectionalEdge(p66, p65);
		addBidirectionalEdge(p66, v2);
		addBidirectionalEdge(p66, v0);
		addBidirectionalEdge(p63, v14);
		addBidirectionalEdge(p67, v2);
		addBidirectionalEdge(p67, v10);
		addBidirectionalEdge(p63, v8);
		addBidirectionalEdge(p58, p68);
		addBidirectionalEdge(v26, p68);
		addBidirectionalEdge(p58, p69);
		addBidirectionalEdge(v27, p69);
		addBidirectionalEdge(p71, p70);
		addBidirectionalEdge(p71, p20);
		addBidirectionalEdge(p71, p72);
		addBidirectionalEdge(p64, p73);
		addBidirectionalEdge(p70, p73);
		addBidirectionalEdge(p24, p74);
		addBidirectionalEdge(v24, p74);
		addBidirectionalEdge(p26, p74);
		addBidirectionalEdge(p75, p74);
		addBidirectionalEdge(p75, p72);
		addBidirectionalEdge(v43, p75);
		addBidirectionalEdge(v44, p73);
		addBidirectionalEdge(v23, p70);
		addBidirectionalEdge(p57, p76);
		addBidirectionalEdge(p77, p76);
		addBidirectionalEdge(p77, p60);
		addBidirectionalEdge(p77, p70);
		addBidirectionalEdge(v20, v16);
		addBidirectionalEdge(v42, v16);
		addBidirectionalEdge(v36, v16);
		addBidirectionalEdge(p49, v12);
		
		
		Node[] vertices = { v0, v1, v2, v3, v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27,v28,v29,v30,v31,v32,v33,v34,v35,v36,v37,v38,v39,v40,v41,v42,v43,v44,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24,p25,p26,p27,p28,p29,p30,p31,p32,p33,p34,p35,p36,p37,p38,p39,p40,p41,p42,p43,p44,p45,p46,p47,p48,p49,p50,p51,p52,p53,p54,p55,p56,p57,p58,p59,p60,p61,p62,p63,p64,p65,p66,p67,p68,p69,p70,p71,p72,p73,p74,p75,p76,p77};

		
		this.setPoints(vertices);
		
		

		//Creates a hash map for buildings only
		buildingList = new LinkedHashMap<String,Node>();
		for (Node v : MapPoints){
			if (v.isBuilding())
				buildingList.put(v.getName() + " (" + v.getCode() + ")" , v);
		}
		
		//Computes edge lengths
		computeLinkLengths();
	     
	}


}
