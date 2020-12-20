package gameClient.util;


import Server.Game_Server_Ex2;
import api.*;
import gameClient.*;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * This class shows us the graph and the course of the drink on our computer screen
 * updates and shows at each shift the current state of the Pokemon and Agents graph
 */
public class panel extends JPanel {

    private static Arena _ar;
    private int ind;
    private gameClient.util.Range2Range gameC;
    private game_service game;
    private float Timer;

    public panel(){
        super();
        ind=0;
        this.setBackground(Color.white);
        _ar= new Arena();

    }



	public void update(Arena ar) {
		this._ar = ar;
		Timer = ar.getTime();
		updateFrame();

	}

	/**
	 * This method update the frame over and over
	 */
	private void updateFrame() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		gameC = Arena.w2f(g,frame);
	}

	/**
	 * 	 * This method Paint oll the Components over and over
	 * @param g
	 */
	public void paint(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		g.clearRect(0, 0, w, h);
		updateFrame();
		drawPokemons(g);
		drawGraph(g);
		drawAgants(g);
		drawInfo(g);
		drawTimer(g);

	}

	/**
	 * This method presents the timer of the game
	 * @param g
	 */
	private void drawTimer(Graphics g){
		g.drawString("Time to end: "+"00:"+(int)Timer/1000,100,135);

	}

	/**
	 * This method presents the info of oll agents
	 * @param g
	 */
	private void drawInfo(Graphics g) {
    	String s="";
    	int counter=10;
		for (CL_Agent agent: _ar.getAgents()) {
			s = "Agent: " + agent.getID() + " Value: " + agent.getValue() + " Speed: " + agent.getSpeed();
			g.drawString(s, 100, 60+counter + 1 * 20);
			counter+=15;
		}
	}

	/**
	 * This method presents the graph
	 * @param g
	 */
	private void drawGraph(Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		while(iter.hasNext()) {
			node_data n = iter.next();
			g.setColor(Color.blue);
			drawNode(n,5,g);
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext()) {
				edge_data e = itr.next();
				g.setColor(Color.gray);
				drawEdge(e, g);
			}
		}
	}

	/**
	 * This method presents the pokemons on the graph
	 * @param g
	 */
	private void drawPokemons(Graphics g) {
		CL_Pokemon[] fs= _ar.getPokemons().toArray(new CL_Pokemon[0]);
		for (int i = 0; i < fs.length; i++) {
			Point3D c = fs[i].getLocation();
			int r=10;
			g.setColor(Color.green);
			if(fs[i].getType()<0) {g.setColor(Color.orange);}
			if(c!=null) {
				geo_location fp = this.gameC.world2frame(c);
				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
				//g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);

			}
		}


	}

	/**
	 * This method presents the agent on the graph
	 * @param g
	 */
	private void drawAgants(Graphics g) {
		List<CL_Agent> rs = _ar.getAgents();
	//	Iterator<OOP_Point3D> itr = rs.iterator();
		g.setColor(Color.red);
		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
			int r=8;
			i++;
			if(c!=null) {

				geo_location fp = this.gameC.world2frame(c);
				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
			}
		}
	}

	/**
	 * This method presents the nodes on the graph
	 * @param n
	 * @param r
	 * @param g
	 */
	private void drawNode(node_data n, int r, Graphics g) {
		geo_location pos = n.getLocation();
		geo_location fp = this.gameC.world2frame(pos);
		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	}

	/**
	 * This method presents the edge on the graph
	 * @param e
	 * @param g
	 */
	private void drawEdge(edge_data e, Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this.gameC.world2frame(s);
		geo_location d0 = this.gameC.world2frame(d);
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
	//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
	}
}


