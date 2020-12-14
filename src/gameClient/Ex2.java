package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Gframe;
import gameClient.util.panel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;

public class Ex2 implements Runnable{
	private static int count=0;
	private static Gframe _win;
	private static Arena _ar;
	private static panel pa;

	public static void main(String[] a) {
		Thread client = new Thread(new Ex2());
		client.start();
	}

	@Override
	public void run() {
		int lvl_num = 23;
		game_service game = Game_Server_Ex2.getServer(lvl_num); // you have [0,23] games
		//	int id = 999;
		//	game.login(id);
		String sg = game.getGraph();
		String pok = game.getPokemons();
		// directed_weighted_graph gg = DWGraph_Algo.jsondeserializer.deserialize(sg);
		System.out.println(sg);
		DWGraph_Algo g1 = new DWGraph_Algo();
		DWGraph_DS gra = new DWGraph_DS();
		g1.init(gra);
		g1.loadfs(sg);
		directed_weighted_graph gg = g1.getGraph();
		//System.out.println(gg);
		init(game);
		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
		int ind = 0;
		long dt = 100;

		////////////////////////////
		while (game.isRunning()) {
			moveAgants(game, gg);
			// _ar.setAgents(Arena.getAgents(game.getAgents(), _ar.getGraph()));
			try {
				if (ind % 1 == 0) {
					_win.repaint();
				}
				sleep(dt);
				ind++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();
		System.out.println(res);
		System.out.println("agent 0 moves: " + count);
		System.exit(0);
	}
	/**
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param
	 */
	private static void moveAgants(game_service game, directed_weighted_graph gg) {
		game.move();
		//String lg = game.move();
		String lgg =game.getAgents();
		// System.out.println(lg);
		// System.out.println(lgg);
		List<CL_Agent> log = Arena.getAgents(lgg, gg);

		_ar.setAgents(log);

		//ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
		String fs = game.getPokemons();
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);

		List<CL_Pokemon> simpl = _ar.getPokemons();
		//   System.out.println(simpl.get(0).getLocation());
		for (int j = 0; j < simpl.size(); j++) {
			Arena.updateEdge(simpl.get(j),gg);
			for (int i = 0; i < log.size(); i++) {
				CL_Agent ag = log.get(i);
				int agid = ag.getID();
				int dest = ag.getNextNode();
				int src = ag.getSrcNode();
				ag.set_curr_fruit(simpl.get(j));
				//   double v = ag.getValue();
				// game.move();

				if (dest == -1) {
					if(_ar.getAgents().get(i).get_curr_fruit().get_edge().getSrc()==_ar.getAgents().get(i).getSrcNode()){
						catchpok(gg,_ar.getAgents().get(i),_ar.getPokemons().get(j),game);
						break;
					}

					//System.out.println("first "+dest);
					dest = nextNode(gg, src, ag.get_curr_fruit());
					game.chooseNextEdge(agid, dest);
					if(dest!=-1) {
						timeredg(gg,src,dest,ag);

						//  long dt = timeredg(gg, src, dest, ag);
						//      try {
						//         sleep(dt);
						//   } catch (InterruptedException e) {
						//      e.printStackTrace();
						//    }
					}

                        /*
                       long dtt= catchpok(gg,src,dest,simpl.get(j),ag) ;
                        try {
                            sleep(dtt);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        */

					// game.move();
					///////////////////////////////
                    /*
                    to be continue........
                    if(log.size()>1&& simpl.size()>1&&i<log.size()-1&&j<simpl.size()-1) {
                        CL_Agent ag2 = log.get(i + 1);
                        int dest2 = ag2.getNextNode();
                        int src2 = ag2.getSrcNode();
                        if (dest2 == -1) {
                            dest2 = nextNode(gg, src2, simpl.get(j + 1));
                            game.chooseNextEdge(ag2.getID(), dest2);
                        }
                    }
                    */
					//print the agents moves:
					//System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
				}


			}
			//System.out.println(simpl.get(j).getLocation());
		}
	}
	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(directed_weighted_graph g, int src,CL_Pokemon pokemon) {
		// System.out.println(pokemon.getLocation());
		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		int s = ee.size();
		//   edge_data e = itr.next();
		// int r = (int) (Math.random() * s);
		while (itr.hasNext()) {
			DWGraph_Algo gaa = new DWGraph_Algo();
			gaa.init(g);
			edge_data e = itr.next();
			if (e.getInfo() != "vis") {

				edge_data pedg= pokemon.get_edge();
				node_data  me=g.getNode(pedg.getSrc());
/*
                    if(gaa.shortestPathDist(src,me.getKey())==-1){
                        System.out.println("enter -1");
                        for (node_data NM : g.getV()) {
                        if(g.getEdge(NM.getKey(),me.getKey())!=null)
                          me=(NodeData)NM;
                        }
                    }
                    */

				List<node_data> shorted = gaa.shortestPath(src, me.getKey());
				while (shorted.size()>1) {
					while (!shorted.isEmpty()) {
						e = g.getEdge(src, shorted.get(1).getKey());
						shorted.remove(0);
						e.setInfo("vis");
						return e.getDest();
					}
				}

				if(shorted.size()==1){
					e.setInfo("vis");
					// long dtt= catchpok(g,src,me.getKey(),pokemon);
					return -1;
				}


				//   double i = Integer.MAX_VALUE;
				//    while (i > shorted) {

				//       i = shorted;
				// }
				//             if (shorted.size() > 1) {
				//  System.out.println(shorted.get(1).getKey());
				//                 ans = me.getKey();
				//           }
				//          ans = e.getDest();


			}
			else{
				e.setInfo("");
			}
		}
		if(ans!=-1) {
			System.out.println("ans not -1");
			edge_data ene = g.getEdge(src, ans);
			ene.setInfo("vis");
		}

		return ans;
	}
	private void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		//gg.init(g);
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		_win = new Gframe("test Ex2");
		_win.setSize(1000, 700);
        pa= new panel();
        _win.add(pa);
		pa.update(_ar);


		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
			for(int a = 0;a<rs;a++) {
				int ind = a%cl_fs.size();
				CL_Pokemon c = cl_fs.get(ind);
				int nn = c.get_edge().getDest();
				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

				game.addAgent(nn);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
	private static long timeredg (directed_weighted_graph gg, int src , int dest, CL_Agent ag){
		double v=ag.getSpeed();
		double s=gg.getEdge(src,dest).getWeight();
		long dt= (long) ((s/v)*1000);
		return dt;
	}
	private static void catchpok( directed_weighted_graph gg,CL_Agent ag, CL_Pokemon pok,game_service game){
		double disedg= gg.getNode(ag.getSrcNode()).getLocation().distance(gg.getNode(pok.get_edge().getDest()).getLocation());
		double we=gg.getEdge(ag.getSrcNode(),pok.get_edge().getDest()).getWeight();
		double dis=  pok.getLocation().distance(gg.getNode(ag.getSrcNode()).getLocation());
		double re= dis/disedg;
		double v=ag.getSpeed();
		long dt = (long) (((re/v)*we)*1000);
		game.chooseNextEdge(ag.getID(),pok.get_edge().getDest());
		try {

			sleep(dt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		game.move();
		System.out.println(ag.getValue());
	}


}



//	private void init(game_service game) {
//		String g = game.getGraph();
//		String fs = game.getPokemons();
//		DWGraph_Algo g1 = new DWGraph_Algo();
//		DWGraph_DS gra = new DWGraph_DS();
//		g1.init(gra);
//		g1.loadfs(g);
//		directed_weighted_graph gg=g1.getGraph();
//		//gg.init(g);
//		_ar = new Arena();
//		_ar.setGraph(gg);
//		_ar.setPokemons(Arena.json2Pokemons(fs));
//		_win = new MyFrame("test Ex2");
//		_win.setSize(1000, 700);
//		_win.update(_ar);
//
//
//		_win.show();
//		String info = game.toString();
//		JSONObject line;
//		try {
//			line = new JSONObject(info);
//			JSONObject ttt = line.getJSONObject("GameServer");
//			int rs = ttt.getInt("agents");
//			System.out.println(info);
//			System.out.println(game.getPokemons());
//			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
//			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
//			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
//			for(int a = 0;a<rs;a++) {
//				int ind = a%cl_fs.size();
//				CL_Pokemon c = cl_fs.get(ind);
//				int nn = c.get_edge().getDest();
//				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}
//
//				game.addAgent(nn);
//			}
//		}
//		catch (JSONException e) {e.printStackTrace();}
//	}
