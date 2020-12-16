package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Gframe;
import gameClient.util.panel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static java.lang.Thread.sleep;

public class Ex2 implements Runnable {
	private static int count = 0;
	private static Gframe _win;
	private static Arena _ar;
	private static panel pa;

	public static void main(String[] a) {
		Thread client = new Thread(new Ex2());
		client.start();
	}

	@Override
	public void run() {
		int lvl_num = 1;
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
	 *
	 * @param game
	 * @param gg
	 * @param
	 */
	private static void moveAgants(game_service game, directed_weighted_graph gg) {
		game.move();
		String lgg = game.getAgents();

		List<CL_Agent> log = Arena.getAgents(lgg, gg);

		_ar.setAgents(log);

		String fs = game.getPokemons();
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);
		//double maxval = 0;
		//int mp = 0;
		List<CL_Pokemon> simpl = _ar.getPokemons();

		//sort pokemons
		//List<CL_Pokemon> sp =simpl;
		sortpoklist(gg,game,simpl);

		//System.out.println("the pokemons:"+simpl.get(0)+simpl.get(1));


		for (int j = 0; j < simpl.size(); j++) {
		Arena.updateEdge(simpl.get(j), gg);
		for (int i = 0; i < log.size(); i++) {
		//	for (int j = 0; j < simpl.size(); j++) {
			//	Arena.updateEdge(simpl.get(j), gg);
				CL_Agent ag = log.get(i);
				int agid = ag.getID();
				int dest = ag.getNextNode();
				int src = ag.getSrcNode();
				ag.set_curr_fruit(simpl.get(0));

				//catch the pokemon in your way even if you on the way to another pokemon with bigger value
				//if(dest==-1 && ag.getSrcNode()==simpl.get(j).get_edge().getSrc()){
				//	System.out.println("entr");
				//	catchpok(gg,ag, simpl.get(j),game);
				//	break;
			//	}
				if (dest == -1) {
				//	if (_ar.getAgents().get(i).get_curr_fruit().get_edge().getSrc() == _ar.getAgents().get(i).getSrcNode()) {
				//		catchpok(gg, _ar.getAgents().get(i), simpl.get(1), game);
				//		break;
				//	}




                    System.out.println(ag.getValue());


				//	System.out.println(ag.getSrcNode());
				//	System.out.println(simpl.get(j).get_edge().getSrc());
					if(ag.getSrcNode()==simpl.get(j).get_edge().getSrc()){
						System.out.println("enter imm");
					//	ag.setNextNode(simpl.get(j).get_edge().getDest());
					//	ag.set_curr_fruit(simpl.get(j));
						catchpok(gg,ag,simpl.get(j),game);
                    //    _ar.setPokemons(simpl);
                        System.out.println(ag.getValue());
						break;
					}



					dest = nextNode(gg, src, ag.get_curr_fruit());
					game.chooseNextEdge(agid, dest);
					if (dest != -1) {
						//count++;
						//System.out.println(count);
						timeredg(gg, src, dest, ag);
					}

				}


			}
		}
	}

	/**
	 * a very simple random walk implementation!
	 *
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(directed_weighted_graph g, int src, CL_Pokemon pokemon) {
		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();

		while (itr.hasNext()) {
			DWGraph_Algo gaa = new DWGraph_Algo();
			gaa.init(g);
			edge_data e = itr.next();

			if (e.getInfo() != "vis") {
				edge_data pedg = pokemon.get_edge();
				node_data me = g.getNode(pedg.getSrc());
				List<node_data> shorted = gaa.shortestPath(src, me.getKey());

				while (shorted.size() > 1) {

					while (!shorted.isEmpty()) {
						e = g.getEdge(src, shorted.get(1).getKey());
						shorted.remove(0);
						e.setInfo("vis");
						return e.getDest();
					}
				}

				if (shorted.size() == 1) {
					e.setInfo("vis");
					return -1;
				}
			} else {
				e.setInfo("");
			}
		}
		if (ans != -1) {
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
		pa = new panel();
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
			for (int a = 0; a < cl_fs.size(); a++) {
				Arena.updateEdge(cl_fs.get(a), gg);
			}
			for (int a = 0; a < rs; a++) {
				int ind = a % cl_fs.size();
				CL_Pokemon c = cl_fs.get(ind);
				int nn = c.get_edge().getDest();
				if (c.getType() < 0) {
					nn = c.get_edge().getSrc();
				}

				game.addAgent(nn);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static void timeredg(directed_weighted_graph gg, int src, int dest, CL_Agent ag) {
		double v = ag.getSpeed();
		double s = gg.getEdge(src, dest).getWeight();
		long dt = (long) ((s / v) * 1000);
		try {
			sleep(dt - 50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Improvement timeredg and catchpok
	private static void catchpok(directed_weighted_graph gg, CL_Agent ag, CL_Pokemon pok, game_service game) {
		double disedg = gg.getNode(ag.getSrcNode()).getLocation().distance(gg.getNode(pok.get_edge().getDest()).getLocation());
		double we = gg.getEdge(ag.getSrcNode(), pok.get_edge().getDest()).getWeight();
		double dis = pok.getLocation().distance(gg.getNode(ag.getSrcNode()).getLocation());
		double re = dis / disedg;
		double v = ag.getSpeed();
		long dt = (long) (((re / v) * we) * 1000);
		game.chooseNextEdge(ag.getID(), pok.get_edge().getDest());
		try {
			sleep(dt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		game.move();
		//Arena.updateEdge(pok, gg);
		// System.out.println(ag.getValue());
	}

	private static void sortpoklist(directed_weighted_graph gg, game_service game, List<CL_Pokemon> lp) {
		lp.sort(new Comparator<CL_Pokemon>() {
			@Override
			public int compare(CL_Pokemon o1, CL_Pokemon o2) {
				if (o1.getValue() > o2.getValue()) return -1;
				if (o1.getValue() < o2.getValue()) return 1;
				return 0;			}
		});
		}





}

