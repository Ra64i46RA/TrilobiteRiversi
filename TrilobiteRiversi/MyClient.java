import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyClient extends JFrame implements MouseListener,MouseMotionListener {
	private JButton buttonArray[][];//�{�^���p�̔z��
	private JButton passbutton;//�p�X�{�^��
	private Container c;
	private ImageIcon boardIcon, bigIcon;//���
	private ImageIcon greenIcon, purpleIcon, mybigIcon, enebigIcon, bigG, bigP;//�v���C���[�̃R�}
	PrintWriter out;//�o�͗p�̃��C�^�[
	private int myColor;
	private int myTurn;
	private int mypasscount, enepasscount;//�p�X�̃J�E���g
	private int boardnum=60; //�ȉ����s����
	static int mynum=2;
	static int enemynum=2;
	private int game=0;      //�ȏ㏟�s����
	private ImageIcon myIcon, yourIcon, passIcon, faceIcon;
	private int temp;//�Ֆʂ̔z��Ƃ��̔z�񂩂犄��o��x,y�̒l
	int iconNum;
	static int resultNum;//���ʂ�MyClientResult�ɑ���
	private ImageIcon firstIcon, secondIcon;//��U��Uicon
	private ImageIcon turnicon;//��U��U�ݒ�icon
	String myNum;//�X�R�A�\��


	public MyClient() {
		//�g�b�v�y�[�W�����p��
		String myName = MyClientTop.playerMyName;
		String playerIpAdress = MyClientTop.playerIpAdress;
		iconNum = MyClientTop.iconnum;


		//�E�B���h�E���쐬����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�E�B���h�E�����Ƃ��ɁC����������悤�ɐݒ肷��
		setTitle("MyClient");//�E�B���h�E�̃^�C�g����ݒ肷��
		setSize(880,600);//�E�B���h�E�̃T�C�Y��ݒ肷��
		c = getContentPane();//�t���[���̃y�C�����擾����
		c.setBackground(new Color(135, 206, 250));



		//�A�C�R���̐ݒ�
		greenIcon = new ImageIcon("GreenTrilobite.jpg");//�΂̃R�}
		purpleIcon = new ImageIcon("PurpleTrilobite.jpg");//���̃R�}
		boardIcon = new ImageIcon("SeafllorGame.jpg");//�R�}��u���ꏊ
		bigIcon = new ImageIcon("EdiacaraFauna.jpg");//���ʂȃR�}��u���ꏊ
		bigG = new ImageIcon("GreenTrilobite_S.jpg");//���ʂȗ΂̃R�}
		bigP = new ImageIcon("PurpleTrilobite_S.jpg");//���ʂȎ��̃R�}

		passIcon = new ImageIcon("pass.jpg");
		firstIcon = new ImageIcon("first.png");//��U
		secondIcon = new ImageIcon("second.png");//��U

		//�����̃L����
		if(iconNum == 1){
			faceIcon = new ImageIcon("fishicon1.png");
		}else if(iconNum == 2){
			faceIcon = new ImageIcon("fishicon2.png");
		}

		c.setLayout(null);//�������C�A�E�g�̐ݒ���s��Ȃ�

		//�{�^���̐���
		buttonArray = new JButton[8][8];//�{�^���̔z����T�쐬����[0]����[7]�܂Ŏg����
		for(int j=0;j<8;j++){//����
			for(int i=0;i<8;i++){//�悱
				buttonArray[j][i]= new JButton(boardIcon);//�{�^���ɃA�C�R����ݒ肷��
				c.add(buttonArray[j][i]);//�y�C���ɓ\��t����
				buttonArray[j][i].setBounds(50+i*45,90+j*45,45,45);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
				buttonArray[j][i].addMouseListener(this);//�{�^�����}�E�X�ł�������Ƃ��ɔ�������悤�ɂ���
				buttonArray[j][i].addMouseMotionListener(this);//�{�^�����}�E�X�œ��������Ƃ����Ƃ��ɔ�������悤�ɂ���
				buttonArray[j][i].setActionCommand(Integer.toString((j*8)+(i)));//�{�^���ɔz��̏���t������i�l�b�g���[�N����ăI�u�W�F�N�g�����ʂ��邽�߁j
			}
		}

		//�p�X�̃{�^��
		passbutton = new JButton(passIcon);
		c.add(passbutton);
		passbutton.setBounds(500,250,200,50);
		passbutton.addMouseListener(this);

		//���O��\��
		JLabel nemeLabel = new JLabel(myName);//���O�p���x��
		c.add(nemeLabel);
		nemeLabel.setBounds(500,50,200,50);
		nemeLabel.setForeground(Color.BLACK);
		nemeLabel.setFont(new Font( "���C���I",Font.BOLD,30));
		nemeLabel.setBackground(new Color(135, 206, 250));
		nemeLabel.setOpaque(true);

		//�����̃A�C�R����\��
		JLabel myfaceLabel = new JLabel(faceIcon);//�A�C�R������
		c.add(myfaceLabel);
		myfaceLabel.setBounds(500,150,100,100);
		myfaceLabel.setOpaque(true);

		//�T�[�o�ɐڑ�����
		Socket socket = null;
		try {
			//"localhost"�́C���������ւ̐ڑ��Dlocalhost��ڑ����IP Address�ɐݒ肷��Ƒ���PC�̃T�[�o�ƒʐM�ł���
			//10000�̓|�[�g�ԍ��DIP Address�Őڑ�����PC�����߂āC�|�[�g�ԍ��ł���PC�㓮�삷��v���O��������肷��
			socket = new Socket(playerIpAdress, 10000);
		} catch (UnknownHostException e) {
			System.err.println("�z�X�g�� IP �A�h���X������ł��܂���: " + e);
		} catch (IOException e) {
			 System.err.println("�G���[���������܂���: " + e);
		}

		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//��M�p�̃X���b�h���쐬����
		mrt.start();//�X���b�h�𓮂����iRun�������j
	}

	//���b�Z�[�W��M�̂��߂̃X���b�h
	public class MesgRecvThread extends Thread {

		Socket socket;
		String myName;

		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}

		//�ʐM�󋵂��Ď����C��M�f�[�^�ɂ���ē��삷��
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//�ڑ��̍ŏ��ɖ��O�𑗂�
				String myNumberStr = br.readLine();
				int myNumber = Integer.parseInt(myNumberStr);
				if(myNumber % 2 == 0){
					myColor = 0;
				}else{
					myColor = 1;
				}

				//��U��U��R�}�̐F�����߂�
				if(myColor == 0){
					myIcon = purpleIcon;
					mybigIcon = bigP;
					enebigIcon = bigG;
					yourIcon = greenIcon;
					myTurn = 0;
					mypasscount = 0;
					enepasscount = 0;
					turnicon = firstIcon;
				}else{
					myIcon = greenIcon;
					yourIcon = purpleIcon;
					mybigIcon = bigG;
					enebigIcon = bigP;
					myTurn = 1;
					mypasscount = 0;
					enepasscount = 0;
					turnicon = secondIcon;
				}

				//��U��U
				JLabel turnLabel = new JLabel(turnicon);//�A�C�R������
				c.add(turnLabel);
				turnLabel.setBounds(500,100,200,50);
				turnLabel.setOpaque(true);


				//�����R�}�̔z�u
				buttonArray[3][3].setIcon(greenIcon);
				buttonArray[3][4].setIcon(purpleIcon);
				buttonArray[4][4].setIcon(greenIcon);
				buttonArray[4][3].setIcon(purpleIcon);

				//�{�[�i�X�R�}�̐ݒu
				buttonArray[2][2].setIcon(bigIcon);
				buttonArray[2][5].setIcon(bigIcon);
				buttonArray[6][5].setIcon(bigIcon);
				buttonArray[6][2].setIcon(bigIcon);

				while(true) {
					String inputLine = br.readLine();//�f�[�^����s�������ǂݍ���ł݂�
					if (inputLine != null) {//�ǂݍ��񂾂Ƃ��Ƀf�[�^���ǂݍ��܂ꂽ���ǂ������`�F�b�N����
						System.out.println(inputLine);//�f�o�b�O�i����m�F�p�j�ɃR���\�[���ɏo�͂���
						String[] inputTokens = inputLine.split(" ");	//���̓f�[�^����͂��邽�߂ɁA�X�y�[�X�Ő؂蕪����
						String cmd = inputTokens[0];//�R�}���h�̎��o���D�P�ڂ̗v�f�����o��
						if(cmd.equals("PLACE")){//cmd�̕�����"PLACE"�����������ׂ�D��������true�ƂȂ�
							//PLACE�̎��̏���
							String theBName = inputTokens[1];//�{�^���̖��O�i�ԍ��j�̎擾
							int theBnum = Integer.parseInt(theBName);//�{�^���̖��O�𐔒l�ɕϊ�����
							int y = theBnum / 8;
							int x = theBnum % 8;
							int theColor = Integer.parseInt(inputTokens[2]);
							System.out.println("myTurn:"+myTurn);
							System.out.println("myColor:"+myColor);
							if(theColor == myColor){
								//���M���N���C�A���g�ł̏���
								if(theBnum == 18||theBnum == 21||theBnum == 50||theBnum == 53){
									buttonArray[y][x].setIcon(mybigIcon);
									mynum = mynum + 5;
								}else{
									buttonArray[y][x].setIcon(myIcon);
									mynum = mynum + 1;
								}
								boardnum = boardnum - 1;
								mypasscount = 0;
								System.out.println("passcount:"+mypasscount);
							}else{
								//���M��N���C�A���g�ł̏���
								if(theBnum == 18||theBnum == 21||theBnum == 50||theBnum == 53){
									buttonArray[y][x].setIcon(enebigIcon);
									enemynum = enemynum + 5;
								}else{
									buttonArray[y][x].setIcon(yourIcon);
									enemynum = enemynum + 1;
								}
								boardnum = boardnum - 1;
								enepasscount = 0;
							}
							myTurn = 1 - myTurn;

							//�I������
							if(boardnum==0 || mypasscount==2 || enepasscount==2|| mynum==0 || enemynum==0){
								if(mynum > enemynum){
									System.out.println("�����̏���");

									//�����ԍ�
									resultNum = 1;
									resultpage();//���ʉ�ʂ�
								}else if(mynum < enemynum){
									System.out.println("����̏���");

									//�s�k�ԍ�
									resultNum = 2;
									resultpage();
								}else if(mynum == enemynum){
									System.out.println("��������");

									//���������ԍ�
									resultNum = 3;
									resultpage();
								}
								game = game + 1;
							}
						}else if(cmd.equals("FLIP")){//cmd��FLIP�����������ׂ�D��������true�ƂȂ�
							//FLIP�̎��̏���
							String theBName = inputTokens[1];//�{�^���̖��O�i�ԍ��j�̎擾
							int theBnum = Integer.parseInt(theBName);//�{�^���̖��O�𐔒l�ɕϊ�����
							int y = theBnum / 8;
							int x = theBnum % 8;
							int theColor = Integer.parseInt(inputTokens[2]);
							if(theColor == myColor){
								//���M���N���C�A���g�ł̏���
								if(theBnum == 18||theBnum == 21||theBnum == 50||theBnum == 53){
									buttonArray[y][x].setIcon(mybigIcon);
									mynum = mynum + 5;
									enemynum = enemynum - 1;
								}else{
									buttonArray[y][x].setIcon(myIcon);
									mynum = mynum + 1;
									enemynum = enemynum - 1;
								}
							}else{
								//���M��N���C�A���g�ł̏���
								if(theBnum == 18||theBnum == 21||theBnum == 50||theBnum == 53){
									buttonArray[y][x].setIcon(enebigIcon);
									mynum = mynum - 1;
									enemynum = enemynum + 5;
								}else{
									buttonArray[y][x].setIcon(yourIcon);
									mynum = mynum - 1;
									enemynum = enemynum + 1;
								}
							}
						}else if(cmd.equals("PASS")){
							//PASS�̎��̏���
							if(myTurn==0){
								mypasscount = mypasscount + 1;
							}else{
								enepasscount = enepasscount + 1;
							}
							myTurn = 1 - myTurn;
							//�I������
							if(boardnum==0 || mypasscount==2 || enepasscount==2|| mynum==0 || enemynum==0){
								if(mynum > enemynum){
									System.out.println("�����̏���");
									resultNum = 1;
									resultpage();
								}else if(mynum < enemynum){
									System.out.println("����̏���");
									resultNum = 2;
									resultpage();
								}else if(mynum == enemynum){
									System.out.println("��������");
									resultNum = 3;
									resultpage();
								}
								game = game + 1;
							}
						}else {
							if(myTurn == 0) {
								//�����p�X���s��
								autpass();
							}
						}

						repaint();


					}else{
						break;
					}

				}
				socket.close();
			} catch (IOException e) {
				System.err.println("�G���[���������܂���: " + e);
			}
		}
	}

	public static void main(String[] args) {
		MyClient net = new MyClient();
		net.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {//�{�^�����N���b�N�����Ƃ��̏���
		int x,y;

		System.out.println("�N���b�N");
		JButton theButton = (JButton)e.getComponent();//�N���b�N�����I�u�W�F�N�g�𓾂�D�^���Ⴄ�̂ŃL���X�g����
		String theArrayIndex = theButton.getActionCommand();//�{�^���̔z��̔ԍ������o��

		Icon theIcon = theButton.getIcon();//theIcon�ɂ́C���݂̃{�^���ɐݒ肳�ꂽ�A�C�R��������
		System.out.println("�N���b�N�����A�C�R��"+theIcon);//�f�o�b�O�i�m�F�p�j�ɁC�N���b�N�����A�C�R���̖��O���o�͂���

		if(game == 0){
			if(theIcon == boardIcon || theIcon == bigIcon){
				temp = Integer.parseInt(theArrayIndex);
					y = temp / 8;
					x = temp % 8;
					System.out.println("x="+x+",y="+y);
				if(myTurn==0){
					//���M�����쐬����i��M���ɂ́C���̑��������ԂɃf�[�^�����o���D�X�y�[�X���f�[�^�̋�؂�ƂȂ�j
					String msg = "PLACE"+" "+theArrayIndex+" "+myColor;
					if(judgeButton(y,x)){
						//�u����
						//�T�[�o�ɏ��𑗂�
						out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
						out.flush();//���M�f�[�^���t���b�V���i�l�b�g���[�N��ɂ͂��o���j����
						repaint();//��ʂ̃I�u�W�F�N�g��`�悵����
					}else{
						//�u���Ȃ�
						System.out.println("�����ɂ͒u���܂���");
					}
				}

			}else if(theIcon == passIcon){
				if(myTurn==0){
				String msg = "PASS"+" "+1414/*msg��PLACE�Ɠ����ɂ��邽�߂̈Ӗ��̂Ȃ����l*/+" "+myColor;
				out.println(msg);
				out.flush();
				System.out.println("�p�X����");
				}

			}
		}
	}

	public void mouseEntered(MouseEvent e) {//�}�E�X���I�u�W�F�N�g�ɓ������Ƃ��̏���

	}

	public void mouseExited(MouseEvent e) {//�}�E�X���I�u�W�F�N�g����o���Ƃ��̏���

	}

	public void mousePressed(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏����i�N���b�N�Ƃ̈Ⴂ�ɒ��Ӂj

	}

	public void mouseReleased(MouseEvent e) {//�}�E�X�ŉ����Ă����I�u�W�F�N�g�𗣂����Ƃ��̏���

	}

	public void mouseDragged(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g�Ƃ��h���b�O���Ă���Ƃ��̏���

	}

	public void mouseMoved(MouseEvent e) {//�}�E�X���I�u�W�F�N�g��ňړ������Ƃ��̏���

	}

	public boolean judgeButton(int y, int x){//�u���邩�u���Ȃ����̔��f
		boolean flag = false;
		for(int j=-1; j<2; j++){
			for(int i=-1; i<2; i++){
				Icon judge;
				if(flipButtons(y,x,j,i)>=1){
					judge=buttonArray[y+j][x+i].getIcon();
					System.out.println("judge="+judge);
					flag = true;
				}
			}
		}
		return flag;
	}

	public int flipButtons(int y,int x,int j,int i){//�Ђ�����Ԃ��邩�̔��f
		int flipNum=0;
		for(int dy=j,dx=i; ; dy+=j, dx+=i){
			if(y+dy<0 || y+dy>7 || x+dx<0 || x+dx>7){
				return 0;
			}else{
				Icon flipIcon = buttonArray[y+dy][x+dx].getIcon();
				if(flipIcon == boardIcon){
					return 0;
				}else if(flipIcon == myIcon){
					break;
				}else if(flipIcon == yourIcon){
					flipNum = flipNum + 1;
				}else if(flipIcon == enebigIcon){
					flipNum = flipNum + 1;
				}else if(flipIcon == bigIcon){
					return 0;
				}else if(flipIcon == mybigIcon){
					break;
				}
			}

		}

		for(int dy=j, dx=i, k=0; k<flipNum; k++, dy+=j, dx+=i){
				//�{�^���̈ʒu�������
					int msgy = y + dy;
					int msgx = x + dx;
					int theArrayIndex = msgy*8 + msgx;
					System.out.println("theArrayIndex="+theArrayIndex);

					//�T�[�o�ɏ��𑗂�
					String msg = "FLIP"+" "+theArrayIndex+" "+myColor;
					out.println(msg);
					out.flush();

		}
		return flipNum;
	}


	private void autpass() {//�����p�X
		if(myTurn == 0) {
			int autpass = 0;//0�Ȃ�p�X�𑗂�
			for(int j=0;j<8;j++){//����
				for(int i=0;i<8;i++){//�悱
					if(judgePass(j,i)){
						autpass = 1;//�p�X�𑗂�Ȃ�
					}else{

					}
				}
			}//����I��
			if(autpass == 0) {//�p�X�𑗂�
				String msg = "PASS"+" "+1414/*msg��PLACE�Ɠ����ɂ��邽�߂̈Ӗ��̂Ȃ����l*/+" "+myColor;
				out.println(msg);
				out.flush();
			}
		}

	}

	//autpass()��for����j,i�����炢�A���̏ꏊ�ŃR�}���u���邩�u���Ȃ����̔��肵�u����Ȃ�true��Ԃ�
	public boolean judgePass(int j, int i){
		boolean pass = false;//false�Ȃ�u���Ȃ�
		for(int m=-1; m<2; m++){//����
			for(int n=-1; n<2; n++){//�悱
				Icon judge;
				if(flipButtons(j,i,m,n)>=1){
					judge=buttonArray[j+m][i+n].getIcon();
					System.out.println("judge="+judge);
					pass = true;
				}
			}
		}
		return pass;
	}

	//autpass()��judgePass��j,i,m,n�����炢�e�ꏊ�ŃR�}��u�����ꍇ�A
	//����₻�̐�ɂЂ�����Ԃ����Ƃ̂��ł���R�}�̗L��
	//�Ђ�����Ԃ��R�}������Ȃ�1�ȏ�A�Ȃ��Ȃ�0��Ԃ�
	public int passcheck(int j,int i,int m,int n){
		int passNum=0;
		for(int dj=m,di=n; ; dj+=m, di+=n){
			if(j+dj<0 || j+dj>7 || i+di<0 || i+di>7){
				return 0;
			}else{
				Icon flipIcon = buttonArray[j+dj][i+di].getIcon();
				if(flipIcon == boardIcon){
					return 0;
				}else if(flipIcon == myIcon){
					break;
				}else if(flipIcon == yourIcon){
					passNum = passNum + 1;
				}else if(flipIcon == enebigIcon){
					passNum = passNum + 1;
				}else if(flipIcon == bigIcon){
					return 0;
				}else if(flipIcon == mybigIcon){
					break;
				}
			}

		}
		return passNum;
	}

	public void resultpage(){
		//���ʉ��
	MyClientResult re = new MyClientResult();//MyClientResult���N��
	System.out.println("���ʉ�ʂ�");
	re.setVisible(true);//MyClientResult��������
	c.setVisible(false); //�E�B���h�E����
	dispose();
	}

}




