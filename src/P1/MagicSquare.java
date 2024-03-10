package P1;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-03-02
 * Time: 16:44
 */
public class MagicSquare {
    //public static int[][] square = new int[300][300];

    public static boolean isLegalMagicSquare(String fileName) throws IOException {
        //分割文本中的\t,\n,提取出一串字符串，
        File file = new File(fileName);
        int[] TwoSum = new int[2];
        Charset charset = StandardCharsets.UTF_8;
        String fileContent = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            fileContent = new String(bytes, charset);
        } catch (FileNotFoundException e) {
            System.out.println("没有找到该文件");
        }
            String []line = fileContent.split("\n");
        int rowNum = line.length;
        int colNum = rowNum;
        int []rowSum = new int[rowNum];
        int []colSum = new int[colNum];
        int[][] square = new int[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            String []m = (line[i]).split("\t");
            for (int j = 0; j < colNum; j++) {
                try {
                    square[i][j] = Integer.valueOf(m[j].trim());
                }catch (NumberFormatException e) {
                    System.out.println("文件中数据格式不正确：例如两数之间用空格而没用'\\t'等等");
                    return false;
                }
                if (square[i][j] < 0) {
                    System.out.println("矩阵中某些数字并非正整数");
                    return false;
                } else {

                    rowSum[i] += square[i][j];
                    colSum[j] += square[i][j];

                    if (i == j) {
                        TwoSum[0] += square[i][j];
                    }
                    if (i + j + 1 == colNum) {
                        TwoSum[1] += square[i][j];
                    }
                }
            }
        }
        if (TwoSum[0] != TwoSum[1]) {
            System.out.println("两条对角线和不相等");
            return false;
        }
        for (int i = 0; i < rowNum; i++) {
            if (TwoSum[0] != rowSum[i] || TwoSum[0] != colSum[i] || rowSum[i] != colSum[i]) {
                System.out.println("行和与对角线和不相等或者列和与对角线和不相等或者行列和不相等");
                return false;
            }
        }
        return true;
    }
        public static boolean generateMagicSquare ( int n){
            try {
                int [][]magic = new int[n][n];
                int row = 0, col = n / 2, i, j, square = n * n;//row行，col列，square是幻方的规模
                for (i = 1; i <= square; i++) {
                    magic[row][col] = i;//第一行中间的数为1，最后一行n/2+1列的数为2
                    if (i % n == 0)//i是行或列的倍数时，行数加1
                        row++;
                    else//i不是n的倍数
                    {
                        if (row == 0)//如果是第一行
                            row = n - 1;//跳转到最后一行
                        else
                            row--;//否则行数减1
                        if (col == (n - 1))//如果是最后一列
                            col = 0;//跳转到第一列
                        else
                            col++;//否则列数加1
                    }
                }
                for (i = 0; i < n; i++) {
                    for (j = 0; j < n; j++)
                        System.out.print(magic[i][j] + "\t");
                    System.out.println();
                }
                //return true;
                //将产生的幻方写入文件6.txt
                try {
                    File file = new File("src/P1/6.txt");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fileWriter);
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < n; j++) {
                            bw.write(magic[i][j] + "\t");
                        }
                        bw.write("\n");
                    }

                    bw.close();
                    System.out.println("finish");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("输入为偶数");
                return false;
            }catch (NegativeArraySizeException e){
                System.out.println("输入为负数");
                return false;
            }
        }

        public static void main (String[]args) throws IOException {
            boolean f = generateMagicSquare(-1);//n为偶数时，由于幻方的起始元素是从第一行中间的右一个开始的，则当i为n的倍数时，row++在某一次迭代后一定会使magic数组越界，故输入偶数不行
            //输入负数不行，因为不存在这样的数组
            System.out.println(f);
            boolean a = isLegalMagicSquare("src/P1/txt/1.txt");
            System.out.println(a);
            boolean b = isLegalMagicSquare("src/P1/txt/2.txt");
            System.out.println(b);
            boolean c = isLegalMagicSquare("src/P1/txt/3.txt");
            System.out.println(c);
            boolean d = isLegalMagicSquare("src/P1/txt/4.txt");
            System.out.println(d);
            boolean e = isLegalMagicSquare("src/P1/txt/5.txt");
            System.out.println(e);
            boolean g=isLegalMagicSquare("src/P1/txt/6.txt");
            System.out.println(g);

        }
    }

