package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Gframe;
import gameClient.util.panel;
import gameClient.util.play;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static java.lang.Thread.getAllStackTraces;
import static java.lang.Thread.sleep;

public class Ex2 implements Runnable {
	private static int count = 0;
	private static Gframe _win;
	private static Arena _ar;
	private static panel pa;
	private static long shortime;
	private static play p;
	private static LinkedList<CL_Pokemon> frupok=new LinkedList<CL_Pokemon>();

	public static void main(String[] a) {
		Thread r = new Thread(){
			public void run(){
				p=new play();
			}
		};
		r.start();
	}

	@Override
	public void run() {
		game_service game = Game_Server_Ex2.getServer(p.getLevel()); // you have [0,23] games
			game.login(p.getId());
		String sg = game.getGraph();
		String pok = game.getPokemons();
		System.out.println(sg);
		DWGraph_Algo g1 = new DWGraph_Algo();
		DWGraph_DS gra = new DWGraph_DS();
		g1.init(gra);
		g1.loadfs(sg);
		directed_weighted_graph gg = g1.getGraph();
		init(game);
		game.startGame();
		////////////////////////game choose next/////////////
		firstchoosenext(game,_ar.getAgents());
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
		while (game.isRunning()) {
			//לבדוק האם אחד הסוכנים מוכן ללכוד-מחזירה סוכן אם הוא מוכן או נאל
			// שליחת סוכן מוכן ללכידה
			// -שורט פאט - ובדיקה אם יש דרך הגעה...שליחה הסוכן לפונקציה שמעדכנת פוקימון מטרה חדשה-בדיקה שאף אחד לא רודף אחריו
			// +טיימר מעבר בין קודקוקודים\\בודק לכל הסוכנים אם יש דסט -1  אם כן כל סוכן שכן בוחר לו דסט הבא לפי השורט פאט
			//לפני טיימר עשה בחירת דסט של הסרבר
			//שמירת הזמן וואייל לזמן הקצר ביותר ושינה וברייק
			CL_Agent redag =checkifcatch(gg,game,_ar.getAgents());
			if(redag!=null){
				catchpok(gg,redag,redag.get_curr_fruit(),game);
                timeraftercatch(gg,redag,redag.get_curr_fruit(),game);

                String fs = game.getPokemons();
                List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
                _ar.setPokemons(ffs);
                String lgg = game.getAgents();
				List<CL_Agent> log = Arena.getAgents(lgg, gg);
				_ar.setAgents(log);


               for (int i = 0; i <_ar.getPokemons().size() ; i++) {
                   Arena.updateEdge(_ar.getPokemons().get(i),gg);
                }
				setpath(gg,game,_ar.getAgents(),_ar.getPokemons());
			}
			if(moveAgants(game, gg)!=Integer.MAX_VALUE){
                try {
                    sleep(shortime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                game.move();
                _win.repaint();
                String lgg = game.getAgents();
                List<CL_Agent> llog = Arena.getAgents(lgg, gg);
                _ar.setAgents(llog);
                String fs = game.getPokemons();
                List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
                _ar.setPokemons(ffs);

				updateagents(_ar.getAgents(),_ar.getPokemons(),gg);

			//	System.out.println(_ar.getAgents());
//                _ar.getAgents().get(i).set_curr_fruit(tarpok);

            }


		}
		String res = game.toString();
		System.out.println(res);
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
	private static long moveAgants(game_service game, directed_weighted_graph gg) {

        List<CL_Agent> log = _ar.getAgents();
        List<CL_Pokemon> simpl = _ar.getPokemons();
        List<CL_Pokemon> sp = new LinkedList<CL_Pokemon>();
        sp = new LinkedList<>(simpl);
        sortpoklist(gg, game, sp);
        long shti = Integer.MAX_VALUE;
        int p = 0;
        for (int i = 0; i < log.size(); i++) {
            CL_Agent ag = log.get(i);
            //System.out.println("in for:ag value  after eat "+ag.getValue());
            //	System.out.println(ag.getNextNode());
            int agid = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            CL_Pokemon tarpok = ag.get_curr_fruit();
            if (dest == -1&&ag.get_curr_fruit()!=null) {
                //ag.set_curr_fruit(sp.get(i));
                //catch the pokemon in your way even if you on the way to another pokemon with bigger value
                //if(dest==-1 && ag.getSrcNode()==simpl.get(j).get_edge().getSrc()){
                //	System.out.println("entr");
                //	catchpok(gg,ag, simpl.get(j),game);
                //	break;
                //	}
                //	if (dest == -1) {
                //	if (_ar.getAgents().get(i).get_curr_fruit().get_edge().getSrc() == _ar.getAgents().get(i).getSrcNode()) {
                //		catchpok(gg, _ar.getAgents().get(i), simpl.get(1), game);
                //		break;
                //	}

                //	System.out.println(ag.getSrcNode());
                //	System.out.println(simpl.get(j).get_edge().getSrc());
                while (p < sp.size()) {
                    Arena.updateEdge(_ar.getPokemons().get(p), gg);
                    if (ag.getSrcNode() == simpl.get(p).get_edge().getSrc()&& !frupok.contains(simpl.get(p))) {
                        //	System.out.println("enter the if src-enter to catch func");
                        catchpok(gg, ag, simpl.get(p), game);
                        Arena.updateEdge(sp.get(p), gg);
                        _ar.setAgents(log);
                        _ar.setPokemons(simpl);
                        //	System.out.println("ag value immediately after eat " + ag.getValue());
                    }
                    p++;
                }

                ///if-1 end

                //System.out.println("the pokemons: " + simpl);
                //System.out.println("the pokemon is the sort list: " + sp);
                dest = nextNode(gg, src, ag.get_curr_fruit(), game);
                ag.setNextNode(dest);

				//uagpok.put(ag.getID(),ag.get_curr_fruit());

//                System.out.println("the src now is: " + src);
           // System.out.println("the dest now is: " + dest);
//                System.out.println("the fruit now is: "+ag.get_curr_fruit());
                game.chooseNextEdge(agid, dest);
                if (timeredg(gg, src, dest, ag) < shti) {
					shti = timeredg(gg, src, dest, ag);
				}
				shortime=shti;
				//	timeredg(gg, src, dest, ag);
                //game.move();
                //_win.repaint();
                //_ar.setAgents(log);
                //_ar.setPokemons(simpl);

			}
        }
        return shti;
    }

	/**
	 * a very simple random walk implementation!
	 *
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(directed_weighted_graph g, int src, CL_Pokemon pokemon,game_service game) {
		int ans = -1;
		//	if (e.getInfo() != "vis") {
		DWGraph_Algo gaa = new DWGraph_Algo();
		gaa.init(g);
		edge_data e = pokemon.get_edge();
		node_data me = g.getNode(e.getSrc());
		List<node_data> shorted = gaa.shortestPath(src, me.getKey());
		//	System.out.println("aaaaaaa");

		if (shorted.size() == 1) {
			e.setInfo("vis");
			//	System.out.println("bbbbb");
			return e.getDest();
		}

		while (!shorted.isEmpty()) {
			e = g.getEdge(src, shorted.get(1).getKey());
			//shorted.remove(0);
			e.setInfo("vis");
			//System.out.println("cccccc");
			return shorted.get(1).getKey();

		}
		//}
		return ans;
	}
	private void init(game_service game) {
		String sg = game.getGraph();
		DWGraph_Algo g1 = new DWGraph_Algo();
		DWGraph_DS gra = new DWGraph_DS();
		g1.init(gra);
		g1.loadfs(sg);
		directed_weighted_graph gg = g1.getGraph();
		String g = game.getGraph();
		String fs = game.getPokemons();
		directed_weighted_graph ggg = game.getJava_Graph_Not_to_be_used();
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
			sortpoklist(ggg,game,cl_fs);
			for (int a = 0; a < rs; a++) {
				//int ind = a % cl_fs.size();
				CL_Pokemon c = cl_fs.get(a);
				int nn = c.get_edge().getDest();
				if (c.getType() < 0) {
					nn = c.get_edge().getSrc();
				}

				game.addAgent(nn);
			}
			_ar.setAgents(Arena.getAgents(game.getAgents(),ggg));
			List<CL_Agent> ags = _ar.getAgents();
			for (int i = 0; i <ags.size() ; i++) {
				ags.get(i).set_curr_fruit(cl_fs.get(i));
				ags.get(i).setNextNode(cl_fs.get(i).get_edge().getDest());
			}
			//after start do game choose next before while running

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static long timeredg(directed_weighted_graph gg, int src, int dest, CL_Agent ag) {
		double v = ag.getSpeed();
		double s = gg.getEdge(src, dest).getWeight();
		long dt = (long) ((s / v) * 1000);
//		try {
//			sleep(dt);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return dt;
	}

	//Improvement timeredg and catchpok
	private static void catchpok(directed_weighted_graph gg, CL_Agent ag, CL_Pokemon pok, game_service game) {
		double disedg = gg.getNode(ag.getSrcNode()).getLocation().distance(gg.getNode(pok.get_edge().getDest()).getLocation());
		double we = gg.getEdge(ag.getSrcNode(), pok.get_edge().getDest()).getWeight();
		double dis = pok.getLocation().distance(gg.getNode(ag.getSrcNode()).getLocation());
		double re = dis / disedg;
		double v = ag.getSpeed();
		long dt = (long) (((re / v) * we) * 1000);
//		System.out.println("ag id: "+ag.getID());
//		System.out.println("ag src: "+ag.getSrcNode());
//		System.out.println("pok src: "+pok.get_edge().getSrc());
//		System.out.println("pok dest: "+pok.get_edge().getDest());
//		System.out.println("pok value: "+pok.getValue());
		game.chooseNextEdge(ag.getID(), pok.get_edge().getDest());
		try {
			sleep(dt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		game.move();
		_win.repaint();
		//Arena.updateEdge(pok, gg);
		// System.out.println(ag.getValue());
	}
    private static void timeraftercatch(directed_weighted_graph gg, CL_Agent ag, CL_Pokemon pok, game_service game) {
        double disedg = gg.getNode(ag.getSrcNode()).getLocation().distance(gg.getNode(pok.get_edge().getDest()).getLocation());
        double we = gg.getEdge(ag.getSrcNode(), pok.get_edge().getDest()).getWeight();
        double dis = pok.getLocation().distance(gg.getNode(pok.get_edge().getDest()).getLocation());
        double re = dis / disedg;
        double v = ag.getSpeed();
        long dt = (long) (((re / v) * we) * 1000);
        try {
            sleep(dt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.move();
        _win.repaint();
        ag.set_curr_fruit(null);
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
private static void firstchoosenext(game_service game, List<CL_Agent> ags){
	for (int i = 0; i < ags.size(); i++) {
		game.chooseNextEdge(ags.get(i).getID(),ags.get(i).getNextNode());

	}
}
private static CL_Agent checkifcatch(directed_weighted_graph gg,game_service game, List<CL_Agent> ags){
//    System.out.println("the agents int he check: "+ags);
	for (int i = 0; i <ags.size() ; i++) {
//       System.out.println("the fruit of the ag in check: "+ags.get(i).get_curr_fruit());


        if(ags.get(i).getSrcNode()==ags.get(i).get_curr_fruit().get_edge().getSrc()) {
//            System.out.println("the fruit of the ag in check: " + ags.get(i).get_curr_fruit());
            return ags.get(i);
        }
	}
	return null;
}
// -שורט פאט - ובדיקה אם יש דרך הגעה...שליחה הסוכן לפונקציה שמעדכנת פוקימון מטרה חדשה-בדיקה שאף אחד לא רודף אחריו
	// +טיימר מעבר בין קודקוקודים\\בודק לכל הסוכנים אם יש דסט -1  אם כן כל סוכן שכן בוחר לו דסט הבא לפי השורט פאט
	//לפני טיימר עשה בחירת דסט של הסרבר
	//שמירת הזמן וואייל לזמן הקצר ביותר ושינה וברייק
private static void setpath(directed_weighted_graph gg,game_service game,List <CL_Agent> ags,List <CL_Pokemon> pok){
	    CL_Agent ag=null;
    for (int a = 0; a <ags.size() ; a++) {
        if(ags.get(a).get_curr_fruit()==null){
             ag =ags.get(a);
            break;
        }
    }
//    System.out.println(ag.getSrcNode());
//	System.out.println(ag.get_curr_fruit());
	sortpoklist(gg,game,pok);
		int j=0;
	for (int i = 0; i < ags.size(); i++) {
		while (ags.get(i).get_curr_fruit()==pok.get(j)) {
			j++;
			i=0;
		}
	}

	DWGraph_Algo g0 = new DWGraph_Algo();
	g0.init(gg);

	if(g0.shortestPathDist(ag.getSrcNode(),pok.get(j).get_edge().getDest())!=-1){
		List<node_data> shorted = g0.shortestPath(ag.getSrcNode(), pok.get(j).get_edge().getDest());
		ag.set_curr_fruit(pok.get(j));
		frupok.add(ag.get_curr_fruit());
		if(shorted.size()==1){
			game.chooseNextEdge(ag.getID(),shorted.get(0).getKey());
		}
		else {
			game.chooseNextEdge(ag.getID(), shorted.get(1).getKey());
		}
        ag.setNextNode(-1);

//        System.out.println("after set -fruit: "+ag.get_curr_fruit());
//        System.out.println("after set "+j);
//        System.out.println("after set "+ag.getSrcNode());
//        System.out.println("after set "+pok.get(j).get_edge().getDest());
//        System.out.println("after set dest: "+ag.getNextNode());

        shorted.remove(0);
	}
}
private static void updateagents (List<CL_Agent> ags, List<CL_Pokemon> pok, directed_weighted_graph gg){
	for (int j = 0; j <ags.size() ; j++) {
		if(ags.get(j).get_curr_fruit()==null)
			ags.get(j).set_curr_fruit(pok.get(j));
	}
	for (int i = 0; i < _ar.getPokemons().size(); i++) {
		Arena.updateEdge(_ar.getPokemons().get(i),gg);
	}
}

}

