package Projet_java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class GraphicGraph{
    //ATTRIBUTS
	private Graphe model;
	private JFrame mainFrame;
	private JTextField currentGraph;
	private JPanel c;
	private JButton ajout;
	private JButton remove;
	private JButton openFile;
	private JButton saveFile;
	private JButton follow;
	private JButton tools;
	
	
	public GraphicGraph() {
		createModel();
        createView();
        placeComponents();
        createController();
	}
	
	public void display() {
        refresh();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
	
	private void createModel() {
		model = new Graphe();
	}
	
    private void createView() {
        final int frameWidth = 700;
        final int frameHeight = 400;
        
        mainFrame = new JFrame("Graphe");
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        
        currentGraph = new JTextField();
        currentGraph.setEditable(false);
        currentGraph.setPreferredSize(new Dimension( 650, 48 ));
    }
    
    private void placeComponents() {
        JPanel n = new JPanel(); {
            n.add(currentGraph);
        }
        ajout = new JButton("ajout sommet");
        remove = new JButton("retrait sommet");
        openFile = new JButton("Ouvrir un fichier");
        saveFile = new JButton("Sauvegarder dans un fichier");
        follow = new JButton("follow/unfollow");
        c = new JPanel(); {
            c.add(ajout);
            c.add(remove);
            c.add(openFile);
            c.add(saveFile);
            c.add(follow);
        }
        tools = new JButton("ouvrir les outils");
        JPanel s = new JPanel(); {
        	s.add(tools);
        }
        mainFrame.add(n, BorderLayout.NORTH);
        mainFrame.add(c, BorderLayout.CENTER);
        mainFrame.add(s, BorderLayout.SOUTH);
    }
    
    private void createController() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ((Observable) model).addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                refresh();
            }
        });

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JFileChooser dialogue = new JFileChooser(new File("."));
            	File fichier;
            	
            	if (dialogue.showOpenDialog(null)== 
            	    JFileChooser.APPROVE_OPTION) {
            	    fichier = dialogue.getSelectedFile();
            	    model.fromFile(fichier.getAbsolutePath());
            	    refresh();
            	}
            }
        });
        
        saveFile.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		if(model.wListAdja() == true) {
        			saveFile.setBackground(Color.green);
        		} else {
        			saveFile.setBackground(Color.red);
        		}
        	}
        });
        
        ajout.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		ajoutFrame();
        	}
        });
        remove.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		removeFrame();
        	}
        });
        follow.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		followFrame();
        	}
        });
        tools.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		toolFrame();
        	}
        });
    }
    
    private void refresh() {
        currentGraph.setText(model.listAdja() == ""?"Graphe est vide":model.listAdja());
        saveFile.setBackground(null);
    }

    // POINT D'ENTREE
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphicGraph().display();
            }
        });
    }
    
    //OUTILS
    private void followFrame() {
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
		    	final int frameWidth = 300;
		        final int frameHeight = 300;
		        
		        final JFrame tmp = new JFrame("follow");
		        tmp.setPreferredSize(new Dimension(frameWidth, frameHeight));
		    	JPanel d1 = new JPanel();
		    	JPanel d2 = new JPanel();
		        JButton doneF = new JButton("Suivre");
		        JButton doneUF = new JButton("Ne plus suivre");
		        final JPanel p = new JPanel(); 
		    	p.add(new JLabel("                                               Suivre                                                 "));
		    	final JComboBox<String> user1 = new JComboBox<String>();
		    	final JComboBox<String> user2 = new JComboBox<String>();
		    	final JComboBox<String> user3 = new JComboBox<String>();
		    	final JComboBox<String> user4 = new JComboBox<String>();
		    	List utilisateur = model.getUtilisateur();
		    	for(int n = 0; n < utilisateur.size(); n++) {
		    		String nom = ((Utilisateur)utilisateur.get(n)).getNom();
		    		user1.addItem(nom);
		    		user3.addItem(nom);
		    	}
		    	for(String nom : model.sortedNom()) {
		    		user2.addItem(nom);
		    		user4.addItem(nom);
		    	}
		    	JLabel l = new JLabel("        suivre             ");
		    	p.add(user1);
		    	p.add(l);
		    	p.add(user2); 
		    	d1.add(doneF);
		    	p.add(d1);
		    	JPanel p1 = new JPanel();
		    	p1.add(new JLabel("                         Ne plus suivre                        "));
		    	JLabel l2 = new JLabel("  ne plus suivre   ");
		    	p1.add(user3);
		    	p1.add(l2);
		    	p1.add(user4);
		    	d2.add(doneUF);
		    	p1.add(d2);
		    	tmp.setLayout(new GridLayout(2, 1));
		        tmp.add(p);
		        tmp.add(p1);
		    	tmp.pack();
		        tmp.setLocationRelativeTo(null);
		        tmp.setVisible(true);
		        
		        doneF.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		if(user1.getSelectedItem() == null) {
		        			user1.setBackground(Color.red);
		        		} else {
		        			model.addFollow((Utilisateur)(model.getInfo((String)user1.getSelectedItem())), 
		        				(Sommet)(model.getInfo((String)user2.getSelectedItem())));
				        	refresh();
					       	tmp.dispose();
		        		}
		        	}
		        });
		        
		        doneUF.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		if(user3.getSelectedItem() == null) {
		        			user3.setBackground(Color.red);
		        		} else {
				        	model.removeFollow((Utilisateur)(model.getInfo((String)user3.getSelectedItem())), 
				        			(Sommet)(model.getInfo((String)user4.getSelectedItem())));
				       		refresh();
				        	tmp.dispose();
		        		}
		        	}
		        });
            }
    	});
    }
    private void ajoutFrame() {
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
		    	final int frameWidth = 300;
		        final int frameHeight = 300;
		        final JFrame tmp = new JFrame("Ajout sommet");
		        tmp.setPreferredSize(new Dimension(frameWidth, frameHeight));
		    	final JTextField nom = new JTextField();
		    	nom.setPreferredSize(new Dimension(200, 24));
		    	final JTextField nomp = new JTextField();
		    	nomp.setPreferredSize(new Dimension(200, 24));
		    	final JTextField prenom = new JTextField();
		    	prenom.setPreferredSize(new Dimension(200, 24));
		    	final JComboBox age = new JComboBox();
		    	for(int n = 13; n < 150; n++) {
		    		age.addItem(n);
		    	}
		    	JPanel d1 = new JPanel();
		    	JPanel d2 = new JPanel();
		    	JButton doneP = new JButton("Ajout Page");
		    	JButton doneU = new JButton("Ajout utilisateur");
		    	JPanel p = new JPanel(); 
		    	p.add(new JLabel("                        Ajout utilisateur                     "));
		    	p.add(new JLabel("  nom :   "));
		    	p.add(nom);
		    	p.add(new JLabel("prenom :"));
		    	p.add(prenom);
		    	p.add(new JLabel("age :"));
		    	p.add(age);
		    	d1.add(doneU);
		    	p.add(d1);
		    	JPanel p1 = new JPanel(); 
		    	p1.add(new JLabel("                                Ajout page                          "));
		    	p1.add(new JLabel("  nom :   "));
		    	p1.add(nomp);
		    	d2.add(doneP);
		    	p1.add(d2);
		    	tmp.setLayout(new GridLayout(2, 1));
		        tmp.add(p);
		        tmp.add(p1);
		    	tmp.pack();
		        tmp.setLocationRelativeTo(null);
		        tmp.setVisible(true);
		        //action des buttons
		        doneU.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		if(nom.getText().isEmpty() || model.sortedNom().contains(nom.getText())) {
		        			nom.setBackground(Color.RED);
		        		} else {
			        		if(prenom.getText().isEmpty()) {
			        			prenom.setBackground(Color.RED);
			        		} else {
				        		Utilisateur user = new Utilisateur(nom.getText(), prenom.getText(), (Integer) age.getSelectedItem());
				        		model.addSommet(user);
				        		refresh();
				        		tmp.dispose();
			        		}
		        		}
		        	}
		        });
		        doneP.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		
		        		if(nomp.getText().isEmpty()) {
		        			nomp.setBackground(Color.RED);
		        		} else {
			        		Page page = new Page(nomp.getText());
			        		model.addSommet(page);
			        		refresh();
			        		tmp.dispose();
		        		}
		        	}
		        });
            }
    	});
    }
    private void removeFrame() {
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
		    	final int frameWidth = 300;
		        final int frameHeight = 300;
		        final JFrame tmp = new JFrame("retrait sommet");
		        tmp.setPreferredSize(new Dimension(frameWidth, frameHeight));
		        
		    	final JComboBox user1 = new JComboBox();
		    	for(String nom : model.sortedNom()) {
		    		user1.addItem(nom);
		    	}
		    	JLabel l = new JLabel("suit");
		    	JPanel p = new JPanel(); 
		    	JButton rm = new JButton("retire sommet");
		    	p.add(user1);
		    	p.add(rm);
		        tmp.add(p);
		    	tmp.pack();
		        tmp.setLocationRelativeTo(null);
		        tmp.setVisible(true);
		        rm.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		if (user1.getSelectedItem() == null) {
		        			user1.setBackground(Color.red);
		        		} else {
			        		model.removeSommet((Sommet)(model.getInfo((String)user1.getSelectedItem())));
			        		refresh();
			        		tmp.dispose();
			        	}
		        	}
		        });
            }
    	});
    }
    private void toolFrame() {
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
		    	final int frameWidth = 300;
		        final int frameHeight = 600;
		        final JFrame tmp = new JFrame("outils");
		        tmp.setPreferredSize(new Dimension(frameWidth, frameHeight));
		    	JPanel p = new JPanel(); 
		    	JLabel j1 = new JLabel("Nombre d'arc");
		    	JLabel j2 = new JLabel("Nombre de sommet");
		    	JLabel j3 = new JLabel("Sommets triés par nom");
		    	JLabel j4 = new JLabel("Sommets triés par degré sortant");
		    	JLabel j5 = new JLabel("Nombre de compte utilisateur");
		    	JLabel j6 = new JLabel("Nombre de compte page");
		    	JLabel j7 = new JLabel("Age moyen des utilisateurs");
		    	JButton b1 = new JButton("Informations sommet");
		    	JButton b2 = new JButton("Administrateurs de page");
		    	JButton b3 = new JButton("Ajouter administrateur");
		    	JButton b4 = new JButton("Plus courte distance");
		    	JTextField t1 = new JTextField(String.valueOf(model.nbAllArc()) + " ");
		    	JTextField t2 = new JTextField(String.valueOf(model.nbSommets()));
		    	JTextField t3 = new JTextField();
		    	for(int i = 0; i < model.sortedNom().size(); ++i) {
		    		t3.setText(t3.getText() + " | " + model.sortedNom().get(i));
		    	}
		    	JTextField t4 = new JTextField();
		    	for (int i = 0; i < model.sortedDegree().size(); ++i){
		    		t4.setText(t4.getText() + " | " +((Sommet)(model.sortedDegree().get(i))).getNom());
		    	}
		    	JTextField t5 = new JTextField(String.valueOf(model.nbUtilisateur()));
		    	JTextField t6 = new JTextField(String.valueOf(model.nbPage()));
		    	JTextField t7 = new JTextField(String.valueOf(model.avgAge()) + " ");
		    	
		    	p.add(j1);
		    	t1.setPreferredSize(new Dimension(250,24));
		    	t1.setEditable(false);
		    	p.add(t1);
		    	p.add(j2);
		    	t2.setPreferredSize(new Dimension(250,24));
		    	t2.setEditable(false);
		    	p.add(t2);		    	
		    	p.add(j3);
		    	t3.setPreferredSize(new Dimension(250,24));
		    	t3.setEditable(false);
		    	p.add(t3);
		    	p.add(j4);
		    	t4.setPreferredSize(new Dimension(250,24));
		    	t4.setEditable(false);
		    	p.add(t4);
		    	p.add(j5);
		    	t5.setPreferredSize(new Dimension(250,24));
		    	t5.setEditable(false);
		    	p.add(t5);
		    	p.add(j6);
		    	t6.setPreferredSize(new Dimension(250,24));
		    	t6.setEditable(false);
		    	p.add(t6);
		    	p.add(j7);
		    	t7.setPreferredSize(new Dimension(250,24));
		    	t7.setEditable(false);
		    	p.add(t7);
		    	p.add(b1);
		    	p.add(b2);
		    	p.add(b3);
		    	p.add(b4);
//		    	JPanel p1 = new JPanel(); 
		        tmp.add(p);
		    	tmp.pack();
		        tmp.setLocationRelativeTo(null);
		        tmp.setVisible(true);
//		        Informations sommet
		        b1.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		infosomm();
		        	}
		        });
//		    	Administrateurs de page
		        b2.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		adminPage();
		        	}
		        });
//		    	Ajouter administrateur
		        b3.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		addAdmin();
		        	}
		        });
//		    	Plus courte distance
		        b4.addActionListener(new ActionListener() {
		        	@Override
		            public void actionPerformed(ActionEvent e) {
		        		shortPage();
		        	}
		        });
            }
    	});
    }
    
    public void infosomm() {
    	final JFrame tmp = new JFrame("Information");
    	final int frameWidth = 300;
        final int frameHeight = 300;
        tmp.setPreferredSize(new Dimension(frameWidth, frameHeight));
        JPanel p = new JPanel();
        final JComboBox user1 = new JComboBox();
        for(String nom : model.sortedNom()) {
    		user1.addItem(nom);
    	}
        JButton apply = new JButton("Voir les informations");
        p.add(user1);
        p.add(apply);
        final JTextArea j = new JTextArea();
//        j.setPreferredSize(new Dimension(300, 24));
        j.setEditable(false);
        p.add(j);
        tmp.add(p);
    	tmp.pack();
        tmp.setLocationRelativeTo(null);
        tmp.setVisible(true);
        apply.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		if (user1.getSelectedItem() == null) {
        			user1.setBackground(Color.red);
        		} else {
	        		Sommet s = (Sommet)(model.getInfo((String)user1.getSelectedItem()));
	        		String result = "Type : " + model.type(s) + "\nPageRank : " + model.pageRank(s) + "\nNom : " + s.getNom();
	        		if(s instanceof Utilisateur) {
	        			Utilisateur u = ((Utilisateur)s);
	        			result = result + "\nPrenom : " + u.getPrenom() + "\nAge : " + String.valueOf(u.getAge());
	        		}
	        		j.setText(result);
        		}
        	}
        });
    }
    public void adminPage() {
    	final JFrame tmp = new JFrame("Admins");
    	final int frameWidth = 300;
        final int frameHeight = 300;
        tmp.setPreferredSize(new Dimension(frameWidth, frameHeight));
        JPanel p = new JPanel();
        final JComboBox page1 = new JComboBox();
        List page = model.getPage();
        for(int n = 0; n < page.size(); n++) {
    		String nom = ((Page)page.get(n)).getNom();
    		page1.addItem(nom);
    	}
        JButton apply = new JButton("Afficher les Admins");
        p.add(page1);
        p.add(apply);
        final JTextArea j = new JTextArea();
//        j.setPreferredSize(new Dimension(300, 24));
        j.setEditable(false);
        p.add(j);
        tmp.add(p);
    	tmp.pack();
        tmp.setLocationRelativeTo(null);
        tmp.setVisible(true);
        apply.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		if (page1.getSelectedItem() == null) {
        			page1.setBackground(Color.red);
        		} else {
	        		Page s = (Page)(model.getInfo((String)page1.getSelectedItem()));
	        		String result = "Administrateur : ";
	        		for(int a = 0; a < s.getAdmin().size(); a++) {
	        			result += ((Sommet)s.getAdmin().get(a)).getNom() + "\n";
	        		}
	        		j.setText(result);
	        	}
        	}
        });
    }
    public void addAdmin() {
    	final JFrame tmp = new JFrame("Ajout Admins");
    	final int frameWidth = 300;
        final int frameHeight = 300;
        tmp.setPreferredSize(new Dimension(frameWidth, frameHeight));
        JPanel p = new JPanel();
        final JComboBox user1 = new JComboBox();
        List user = model.getUtilisateur();
        for(int n = 0; n < user.size(); n++) {
    		String nom = ((Utilisateur)user.get(n)).getNom();
    		user1.addItem(nom);
    	}
        p.add(user1);
        JLabel l = new JLabel(" vas devenir admin de ");
        p.add(l);
        final JComboBox page1 = new JComboBox();
        List page = model.getPage();
        for(int n = 0; n < page.size(); n++) {
    		String nom = ((Page)page.get(n)).getNom();
    		page1.addItem(nom);
    	}
        p.add(page1);
        JButton apply = new JButton("Appliquer");
        p.add(apply);
        tmp.add(p);
    	tmp.pack();
        tmp.setLocationRelativeTo(null);
        tmp.setVisible(true);
        apply.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		if ((page1.getSelectedItem() == null) || (user1.getSelectedItem() == null)){
        			page1.setBackground(Color.red);
        			user1.setBackground(Color.red);
        		} else {
	        		Page s = (Page)(model.getInfo((String)page1.getSelectedItem()));
	        		Utilisateur u = (Utilisateur)(model.getInfo((String)user1.getSelectedItem()));
	        		s.addAdmin(u);
	        		tmp.dispose();
        		}
        	}
        });
    }
    public void shortPage() {
    	final JFrame tmp = new JFrame("distance la plus courte");
    	final int frameWidth = 300;
        final int frameHeight = 300;
        tmp.setPreferredSize(new Dimension(frameWidth, frameHeight));
        JPanel p = new JPanel();
        final JComboBox user1 = new JComboBox();
        List user = model.getUtilisateur();
        for(int n = 0; n < user.size(); n++) {
    		String nom = ((Utilisateur)user.get(n)).getNom();
    		user1.addItem(nom);
    	}
        p.add(user1);
        JLabel l = new JLabel(" avec ");
        p.add(l);
        final JComboBox page1 = new JComboBox();
        List sommet = model.getSommets();
        for(int n = 0; n < sommet.size(); n++) {
    		String nom = ((Sommet)sommet.get(n)).getNom();
    		page1.addItem(nom);
    	}
        p.add(page1);
        JButton apply = new JButton("Afficher la distance");
        p.add(apply);
        final JTextArea j = new JTextArea();
//      j.setPreferredSize(new Dimension(300, 24));
        j.setEditable(false);
        p.add(j);
        tmp.add(p);
    	tmp.pack();
        tmp.setLocationRelativeTo(null);
        tmp.setVisible(true);
        apply.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		if ((page1.getSelectedItem() == null) || (user1.getSelectedItem() == null)){
        			page1.setBackground(Color.red);
        			user1.setBackground(Color.red);
        		} else {
        			Sommet s = (Sommet)(model.getInfo((String)page1.getSelectedItem()));
	        		Sommet u = (Sommet)(model.getInfo((String)user1.getSelectedItem()));
	        		int dist = model.shortest(u, s);
        			String result = ("Distance de " + u.getNom() + " vers " + s.getNom() +" : " + dist) ;
            		j.setText(result);
        		}
        	}
        });
    }
}
    
    
    
    
    