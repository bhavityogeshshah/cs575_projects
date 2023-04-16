public class rotateMatrix {
    public String[][] rotateMatrixImpl(String [][] matrix) {

        for(int i = 0; i < matrix.length;i++){
            for(int j = i; j < matrix[i].length;j++){
                String temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        for(int j = 0;j < matrix.length;j++){
            int left = 0;
            int right = matrix[j].length - 1;

            while(left < right){
                String temp = matrix[left][j];
                matrix[left][j] = matrix[right][j];
                matrix[right][j] = temp;
                left++;
                right--;
            }
        }

        return matrix;


    }
}