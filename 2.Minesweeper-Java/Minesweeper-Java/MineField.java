import java.util.Random;

class MineField{

	private boolean[][] mines,visible;
	private boolean boom;
	private final int rowMax = 5;
	private final int colMax = 10;
	
	MineField(){
		
		mines=new boolean[rowMax][colMax];
		visible=new boolean[rowMax][colMax];
		boom=false;
		
		initMap();
		
		int counter2=15;
		int randomRow,randomCol;
		Random RGenerator=new Random();
		
		while(counter2>0){
			
			randomRow=Math.abs(RGenerator.nextInt()%rowMax);
			randomCol=Math.abs(RGenerator.nextInt()%colMax);
			
			if(trymove(randomRow,randomCol)){
				counter2--;
			}
		}
	}	
	
	private void initMap(){
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				mines[row][col]=false;
				visible[row][col]=false;
			}
		}		
	}
	
	private boolean trymove(int randomRow, int randomCol) {
		if(mines[randomRow][randomCol]) return false;
		else{
			mines[randomRow][randomCol]=true;
			return true;
		}
	}
	private void boom() {
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				if(mines[row][col]) visible[row][col]=true;
			}
		}
		boom=true;
		show();
	
	}
	
	/**
	 * This method draws the numbers on the minefield
	 * @param row is the row of the matrix
	 * @param col is the column of the matrix
	 * @return count as char or 'X' 
	 */
	private char drawChar(int row, int col) {
		int count=0;
		if(visible[row][col]){
			if(mines[row][col]) return '*';
			
			for(int irow=row-1;irow<=row+1;irow++){
				count = countMines(row, col, irow);
			}	
		}
		else if(boom)
			return '-';	
		else return '?';
		
		if(count > 8) return 'X';
		else return (char)(count+49);
		
	}
	/**
	 * Help method used by drawChar. It counts the mines
	 * @param row
	 * @param col
	 * @param irow 
	 * @return count - returns the count
	 */
	private int countMines(int row, int col, int irow){
		int count = 0;
		for(int icol=col-1;icol<=col+1;icol++){
			if(icol>=0&&icol<colMax&&irow>=0&&irow<rowMax&&mines[irow][icol]){
				count++;
			}
		}
		return count;
	}
	
	public boolean getBoom(){
		return boom;
	}


	public boolean legalMoveString(String input) {
		String[] separated=input.split(" ");
		int row;
		int col;
		try{
			row=Integer.parseInt(separated[0]);
			col=Integer.parseInt(separated[1]);
			if(row<0||col<0||row>=rowMax||col>=colMax){
				throw new java.io.IOException();
			}
		}
		catch(Exception e){
			System.out.println("\nInvalid Input!");
			return false;
		}
		
		if(legalMoveValue(row,col))
			return true;
		else
			return false;
		
	}

	private boolean legalMoveValue(int row, int col) {
		
		if(visible[row][col]){
			System.out.println("You stepped in allready revealed area!");
			return false;
		}
		else visible[row][col]=true;

		
		if(mines[row][col]){
			boom();
			return false;
		}
		
		return true;
	}
	
	public void show() {
		System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
		System.out.println("   ---------------------");
		for(int row=0;row<rowMax;row++){
			System.out.print(row+" |");
			for(int col=0;col<colMax;col++){
				System.out.print(" "+drawChar(row,col));
				
			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}
	
}
