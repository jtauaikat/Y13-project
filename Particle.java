import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particle {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int size;
    private Color color;
    private int lifespan;
     
    public Particle(int x, int y) {
        this.x = x;
        this.y = y;

        Random rand = new Random();
        this.vx = rand.nextInt(5) - 2;
        this.vy = rand.nextInt(5) - 2;
        this.size = rand.nextInt(5) + 2;
        //System.out.println(x);
        //System.out.println(y);

        this.color = new Color(200,0,0);
        this.lifespan = 10 + rand.nextInt(20);
    }

    public boolean isAlive() {
        return lifespan > 0;
    }

    public void update() {
        x += vx;
        y += vy;
        lifespan--;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }
}
