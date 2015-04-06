package equationsolver;

import elements.Element;

public class Matrix {
	private int size;
	public double[][] data;
	
	public Matrix(int size){
		this.size = size;
		data = new double[size][size];
	}
	
	public Matrix getInverse(){
		double[][] a = new double[size][size];
		double[][] x = new double[size][size];
		double[][] b = new double[size][size];
		int index[] = new int[size];
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++)
				a[i][j] = data[i][j];
			b[i][i] = 1;
		}
		
		double[] c = new double[size];
		for (int i = 0; i < size; ++i){
			index[i] = i;
            double c1 = 0;
            for (int j = 0; j < size; ++j){
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1)
                	c1 = c0;
            }
            c[i] = c1;
        }
		int l = 0;
        for (int j = 0; j < size - 1; ++j){
            double pi1 = 0;
            for (int i = j; i < size; ++i){
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1){
                    pi1 = pi0;
                    l = i;
                }
            }
            int itmp = index[j];
            index[j] = index[l];
            index[l] = itmp;
            for (int i = j + 1; i < size; ++i){
                double pj = a[index[i]][j] / a[index[j]][j];
                a[index[i]][j] = pj;
                for (int m = j + 1; m < size; ++m)
                    a[index[i]][m] -= pj * a[index[j]][m];
            }
        }
		
		for (int i = 0; i < size - 1; ++i)
			for (int j = i + 1; j < size; ++j)
				for (int k = 0; k < size; ++k)
                    b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];
		
		for (int i = 0; i < size; ++i){
            x[size - 1][i] = b[index[size - 1]][i] / a[index[size - 1]][size - 1];
            for (int j = size - 2; j >= 0; --j){
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < size; ++k)
                    x[j][i] -= a[index[j]][k] * x[k][i];
                x[j][i] /= a[index[j]][j];
            }
        }
		
		Matrix inverse = new Matrix(size);
		inverse.data = x;
        return inverse;
	}
	
	public String toString(){
		String output = "";
		for (int i = 0; i < size; i ++){
			for (int j = 0; j < size; j ++)
				output += Element.doubleToString(data[i][j]) + "\t";
			output += "\n";
		}
		return output;
	}
	
	public static void main(String[] args){
		Matrix a = new Matrix(2);
		a.data = new double[][]{{1, 2},{3, 2}};
		System.out.println(a);
		System.out.println(a.getInverse());
		System.out.println(a);
	}
}
