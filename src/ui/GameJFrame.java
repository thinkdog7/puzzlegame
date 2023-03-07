package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //创建二维数组，用来管理数据
    int[][] data = new int[4][4];
    //记录空白方块在二维数组中的位置
    int x = 0;
    int y = 0;
    String path = "img/img2/";

    int[][] win = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    //统计步数
    int step = 0;
    JMenuItem reGame = new JMenuItem("重新开始");
    JMenuItem reLogin = new JMenuItem("重新登录");
    JMenuItem closeGame = new JMenuItem("关闭游戏");
    JMenuItem helpWindow = new JMenuItem("帮助");

    public GameJFrame() {

        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //打乱图片
        initData();
        //初始化图片
        initImage();
        //设置显示
        this.setVisible(true);
    }

    private void initData() {
        //创建一维数组
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        //随机交换
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        //将交换后的值添加到二维数组
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    private void initImage() {
        this.getContentPane().removeAll();
        if (victory()) {
            JLabel winJLabel = new JLabel(new ImageIcon("img/victory.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }
        JLabel stepCount = new JLabel("步数：" + step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //获取当前要加载的图片序号
                int number = data[i][j];
                //创建一个图片ImageIcon对象
                ImageIcon icon = new ImageIcon(path + number + ".jpg");
                //创建一个JLabel的对象（管理容器）
                JLabel jLabel = new JLabel(icon);
                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //给小图片添加边框
                jLabel.setBorder(new BevelBorder(1));
                //把管理容器添加到界面
                this.getContentPane().add(jLabel);
            }
        }
        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("img/background.png"));
        background.setBounds(40, 37, 508, 560);
        //把背景图片添加到界面中
        this.getContentPane().add(background);
        //刷新
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        //创建整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        //创建两个选项
        JMenu function = new JMenu("功能");
        JMenu help = new JMenu("帮助");
        //创建条目

        //将选项和条目添加到菜单
        function.add(reGame);
        function.add(reLogin);
        function.add(closeGame);

        help.add(helpWindow);
        //给条目绑定事件
        reGame.addActionListener(this);
        reLogin.addActionListener(this);
        closeGame.addActionListener(this);
        helpWindow.addActionListener(this);


        jMenuBar.add(function);
        jMenuBar.add(help);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //设置界面宽高
        this.setSize(603, 680);
        //设置界面的标题
        this.setTitle("拼图游戏 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 65) {
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            //添加背景图片
            JLabel background = new JLabel(new ImageIcon("img/background.png"));
            background.setBounds(40, 37, 508, 560);
            //把背景图片添加到界面中
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利，胜利直接结束
        if (victory()) {
            return;
        }
        //对上下左右进行判断
        //左：37 上：38 右39 下：40
        int code = e.getKeyCode();
        if (code == 37) {
            System.out.println("左移");
            if (y == 3) {
                //空白方块在最右方了，不能再移动了
                return;
            }
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
            initImage();

        } else if (code == 38) {
            System.out.println("上移");
            if (x == 3) {
                //空白方块在最下方了，不能再移动了
                return;
            }
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;
            initImage();
        } else if (code == 39) {
            System.out.println("右移");
            if (y == 0) {
                //空白方块在最左方了，不能再移动了
                return;
            }
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
            initImage();

        } else if (code == 40) {
            System.out.println("下移");
            if (x == 0) {
                //空白方块在最上方了，不能再移动了
                return;
            }
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            step++;
            initImage();
        } else if (code == 65) {//A键
            initImage();
        } else if (code == 87) {//W键
            data = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
            initImage();
        }
    }

    //判断data数组的数据是否和win数组相同，相同true，不同false
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == reGame) {
            //计步器清零
            step = 0;
            //打乱数据
            initData();
            //重新加载图片
            initImage();


        } else if (obj == helpWindow) {
            //创建一个弹框对象
            JDialog jDialog = new JDialog();
            // 创建一个面板
            JPanel jPanel = new JPanel();
            jPanel.setBounds(300, 300, 300, 300);// 设置窗口的坐标和大小
            jPanel.setVisible(true);// 设置窗口可见
            String text = "使用键盘的上下左右方向键移动图片，按A查看正确的完整图片，按W可直接通关。";
            JTextArea jTextArea = new JTextArea(text, 5, 5);
            jTextArea.setLineWrap(true);
            jPanel.add(jTextArea);
            jDialog.add(jPanel);
            jDialog.setSize(344, 344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.setVisible(true);
        } else if (obj == closeGame) {
            System.exit(0);
        }
    }
}
