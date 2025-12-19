//package org.example;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) throws SQLException {
//        DataRetriever dataRetriever = new DataRetriever();
//        System.out.println(dataRetriever.findTeamById(1));
//        System.out.println(dataRetriever.findTeamById(5));
//        System.out.println(dataRetriever.findPlayers(1,2));
//        System.out.println(dataRetriever.findPlayers(3,5));
//        System.out.println(dataRetriever.findTeamsByPlayerName("an"));
//        System.out.println(dataRetriever.findPlayersByCriteria("ud",PlayerPositionEnum.MIDF,"Madrid",ContinentEnum.EUROPA,1,10));
//
//    }
//}
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {
    private static DataRetriever dataRetriever = new DataRetriever();

    public static void main(String[] args) {
        System.out.println("=== TESTS SUJET TD2 Java & PostgreSQL ===");
        System.out.println("==========================================\n");

        try {
            // Préparation: s'assurer que les données de test sont propres
            cleanTestData();

            // Exécution des tests
            testA();
            testB();
            testC();
            testD();
            testE();
            testF();
            testG();
            testH();
            testI();
            testJ();

            System.out.println("\n=== TOUS LES TESTS SONT TERMINÉS ===");

        } catch (Exception e) {
            System.err.println("Erreur lors des tests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void cleanTestData() {
        System.out.println("Nettoyage des données de test...");
        // Supprimer les joueurs de test s'ils existent
        deletePlayerIfExists(6); // Jude Bellingham
        deletePlayerIfExists(7); // Pedri
        deletePlayerIfExists(8); // Vini (si créé précédemment)

        // Réinitialiser Real Madrid à ses joueurs d'origine
        resetRealMadrid();

        // Réinitialiser FC Barcelone à ses joueurs d'origine
        resetBarcelona();

        System.out.println("Nettoyage terminé.\n");
    }

    private static void testA() {
        System.out.println("Test a) findTeamById(1) - Real Madrid avec 3 joueurs");
        System.out.println("---------------------------------------------------");

        try {
            Team realMadrid = dataRetriever.findTeamById(1);

            if (realMadrid == null) {
                System.out.println("❌ ÉCHEC: L'équipe n'a pas été trouvée");
                return;
            }

            System.out.println("Équipe trouvée: " + realMadrid.getName());
            System.out.println("Continent: " + realMadrid.getContinent());
            System.out.println("Nombre de joueurs: " + realMadrid.getPlayers().size());

            // Vérification spécifique
            if (realMadrid.getName().equals("Real Madrid") &&
                    realMadrid.getPlayers().size() == 3) {
                System.out.println("✓ SUCCÈS: Real Madrid avec 3 joueurs");
                System.out.println("Joueurs:");
                for (Player p : realMadrid.getPlayers()) {
                    System.out.println("  - " + p.getName() + " (" + p.getPosition() + ")");
                }
            } else {
                System.out.println("❌ ÉCHEC: Données incorrectes");
            }

        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testB() {
        System.out.println("Test b) findTeamById(5) - Inter Miami avec liste vide");
        System.out.println("----------------------------------------------------");

        try {
            Team interMiami = dataRetriever.findTeamById(5);

            if (interMiami == null) {
                System.out.println("❌ ÉCHEC: L'équipe n'a pas été trouvée");
                return;
            }

            System.out.println("Équipe trouvée: " + interMiami.getName());
            System.out.println("Continent: " + interMiami.getContinent());
            System.out.println("Nombre de joueurs: " + interMiami.getPlayers().size());

            // Vérification spécifique
            if (interMiami.getName().equals("Inter Miami") &&
                    interMiami.getPlayers().isEmpty()) {
                System.out.println("✓ SUCCÈS: Inter Miami avec 0 joueur");
            } else {
                System.out.println("❌ ÉCHEC: Données incorrectes");
            }

        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testC() {
        System.out.println("Test c) findPlayers(1, 2) - Page 1, taille 2");
        System.out.println("--------------------------------------------");

        try {
            List<Player> players = dataRetriever.findPlayers(1, 2);

            System.out.println("Nombre de joueurs retournés: " + players.size());

            if (players.size() == 2) {
                Player p1 = players.get(0);
                Player p2 = players.get(1);

                System.out.println("Joueur 1: " + p1.getName());
                System.out.println("Joueur 2: " + p2.getName());

                // Selon le sujet, devrait être Thibaut Courtois et Dani Carvajal
                // Mais cela dépend de l'ordre dans la base
                if (p1.getName().contains("Courtois") && p2.getName().contains("Carvajal")) {
                    System.out.println("✓ SUCCÈS: Thibaut Courtois et Dani Carvajal trouvés");
                } else if (p1.getName().contains("Carvajal") && p2.getName().contains("Courtois")) {
                    System.out.println("✓ SUCCÈS: Dani Carvajal et Thibaut Courtois trouvés");
                } else {
                    System.out.println("⚠ ATTENTION: Joueurs différents des attendus");
                    System.out.println("  Attendu: Thibaut Courtois et Dani Carvajal");
                }
            } else {
                System.out.println("❌ ÉCHEC: Devrait retourner 2 joueurs");
            }

        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testD() {
        System.out.println("Test d) findPlayers(3, 5) - Page 3, taille 5 (liste vide)");
        System.out.println("--------------------------------------------------------");

        try {
            List<Player> players = dataRetriever.findPlayers(3, 5);

            System.out.println("Nombre de joueurs retournés: " + players.size());

            if (players.isEmpty()) {
                System.out.println("✓ SUCCÈS: Liste vide comme attendu");
            } else {
                System.out.println("❌ ÉCHEC: La liste devrait être vide");
                System.out.println("Joueurs trouvés:");
                for (Player p : players) {
                    System.out.println("  - " + p.getName());
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testE() {
        System.out.println("Test e) findTeamsByPlayerName(\"an\")");
        System.out.println("------------------------------------");

        try {
            List<Team> teams = dataRetriever.findTeamsByPlayerName("an");

            System.out.println("Nombre d'équipes trouvées: " + teams.size());

            // Selon le sujet: devrait trouver Real Madrid et Atletico Madrid
            Set<String> expectedTeams = new HashSet<>(Arrays.asList("Real Madrid", "Atletico Madrid"));
            Set<String> foundTeams = new HashSet<>();

            for (Team team : teams) {
                System.out.println("Équipe: " + team.getName());
                foundTeams.add(team.getName());

                System.out.println("  Joueurs de l'équipe:");
                for (Player p : team.getPlayers()) {
                    System.out.println("    - " + p.getName() + " (contient 'an': " +
                            p.getName().toLowerCase().contains("an") + ")");
                }
            }

            if (foundTeams.containsAll(expectedTeams) && foundTeams.size() == 2) {
                System.out.println("✓ SUCCÈS: Real Madrid et Atletico Madrid trouvés");
            } else {
                System.out.println("⚠ Résultat: Trouvé " + foundTeams);
                System.out.println("  Attendu: [Real Madrid, Atletico Madrid]");
            }

        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testF() {
        System.out.println("Test f) findPlayersByCriteria avec filtres multiples");
        System.out.println("----------------------------------------------------");
        System.out.println("Critères:");
        System.out.println("  - playerName: \"ud\"");
        System.out.println("  - position: MIDF");
        System.out.println("  - teamName: \"Madrid\"");
        System.out.println("  - continent: EUROPA");
        System.out.println("  - page: 1, size: 10");
        System.out.println("Attendu: Jude Bellingham");
        System.out.println("------------------------------------");

        try {
            List<Player> players = dataRetriever.findPlayersByCriteria(
                    "ud",
                    PlayerPositionEnum.MIDF,
                    "Madrid",
                    ContinentEnum.EUROPA,
                    1,
                    10
            );

            System.out.println("Nombre de joueurs trouvés: " + players.size());

            if (players.size() == 1) {
                Player player = players.get(0);
                System.out.println("Joueur trouvé: " + player.getName());
                System.out.println("Position: " + player.getPosition());
                System.out.println("Équipe: " +
                        (player.getTeam() != null ? player.getTeam().getName() : "Aucune"));

                if (player.getName().equals("Jude Bellingham") &&
                        player.getPosition() == PlayerPositionEnum.MIDF) {
                    System.out.println("✓ SUCCÈS: Jude Bellingham trouvé");
                } else {
                    System.out.println("❌ ÉCHEC: Mauvais joueur trouvé");
                }
            } else if (players.isEmpty()) {
                System.out.println("❌ ÉCHEC: Aucun joueur trouvé");
                System.out.println("  Vérifiez que Jude Bellingham existe dans la base avec:");
                System.out.println("    - ID: 6");
                System.out.println("    - Nom: Jude Bellingham");
                System.out.println("    - Position: MIDF");
                System.out.println("    - Équipe: Real Madrid (id_team = 1)");
            } else {
                System.out.println("❌ ÉCHEC: Trop de joueurs trouvés");
                for (Player p : players) {
                    System.out.println("  - " + p.getName() + " (" + p.getPosition() + ")");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ ERREUR SQL: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testG() {
        System.out.println("Test g) createPlayers avec Jude Bellingham existant");
        System.out.println("---------------------------------------------------");
        System.out.println("Scénario: Jude Bellingham (ID=6) existe déjà");
        System.out.println("Attendu: RuntimeException et aucun joueur créé");
        System.out.println("------------------------------------");

        try {
            // S'assurer que Jude Bellingham existe
            ensureJudeBellinghamExists();

            // Préparer la liste avec Jude Bellingham (existant) et Pedri (nouveau)
            List<Player> newPlayers = new ArrayList<>();
            Player jude = new Player(6, "Jude Bellingham", 23, PlayerPositionEnum.STR, null);
            Player pedri = new Player(7, "Pedri", 24, PlayerPositionEnum.MIDF, null);
            newPlayers.add(jude);
            newPlayers.add(pedri);

            System.out.println("Tentative de création de 2 joueurs...");
            System.out.println("  - Jude Bellingham (ID=6) - EXISTE DÉJÀ");
            System.out.println("  - Pedri (ID=7) - NOUVEAU");

            try {
                List<Player> created = dataRetriever.createPlayers(newPlayers);

                // Si on arrive ici, c'est une erreur
                System.out.println("❌ ÉCHEC: Aucune exception levée!");
                System.out.println("  " + created.size() + " joueurs créés");

                // Vérifier que Pedri n'a pas été créé (atomicité)
                if (playerExists(7)) {
                    System.out.println("  ⚠ ERREUR GRAVE: Pedri a été créé malgré l'échec!");
                    System.out.println("    L'atomicité n'est pas respectée");
                }

            } catch (RuntimeException e) {
                System.out.println("✓ SUCCÈS: RuntimeException levée");
                System.out.println("  Message: " + e.getMessage());

                // Vérifier l'atomicité: Pedri ne doit pas exister
                if (!playerExists(7)) {
                    System.out.println("✓ Atomicité vérifiée: Pedri n'a pas été créé");
                } else {
                    System.out.println("❌ ÉCHEC Atomicité: Pedri a été créé!");
                }

                // Vérifier que Jude existe toujours
                if (playerExists(6)) {
                    System.out.println("✓ Jude Bellingham existe toujours (non affecté)");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ ERREUR: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testH() {
        System.out.println("Test h) createPlayers avec nouveaux joueurs Vini et Pedri");
        System.out.println("--------------------------------------------------------");
        System.out.println("Scénario: Aucun des joueurs n'existe");
        System.out.println("Attendu: Les 2 joueurs sont créés");
        System.out.println("------------------------------------");

        try {
            // Nettoyer les joueurs de test
            deletePlayerIfExists(6); // Vini
            deletePlayerIfExists(7); // Pedri

            // Préparer la liste
            List<Player> newPlayers = new ArrayList<>();
            Player vini = new Player(6, "Vini", 25, PlayerPositionEnum.STR, null);
            Player pedri = new Player(7, "Pedri", 24, PlayerPositionEnum.MIDF, null);
            newPlayers.add(vini);
            newPlayers.add(pedri);

            System.out.println("Tentative de création de 2 nouveaux joueurs...");
            System.out.println("  - Vini (ID=6)");
            System.out.println("  - Pedri (ID=7)");

            try {
                List<Player> created = dataRetriever.createPlayers(newPlayers);

                System.out.println("Résultat: " + created.size() + " joueurs créés");

                if (created.size() == 2) {
                    System.out.println("✓ SUCCÈS: Les 2 joueurs ont été créés");

                    // Vérifier qu'ils existent en base
                    if (playerExists(6) && playerExists(7)) {
                        System.out.println("✓ Vérification en base: Les 2 joueurs existent");
                    } else {
                        System.out.println("⚠ ATTENTION: Problème de vérification en base");
                    }
                } else {
                    System.out.println("❌ ÉCHEC: " + created.size() + " joueurs créés au lieu de 2");
                }

            } catch (RuntimeException e) {
                System.out.println("❌ ÉCHEC: RuntimeException inattendue: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("❌ ERREUR: " + e.getMessage());
        }
        System.out.println();
    }

    private static void testI() {
        System.out.println("Test i) saveTeam - Ajouter Vini à Real Madrid");
        System.out.println("---------------------------------------------");
        System.out.println("Scénario: Ajouter Vini (ID=6) à Real Madrid sans remplacer les autres");
        System.out.println("------------------------------------");

        try {
            // S'assurer que Vini existe
            ensureViniExists();

            // Récupérer Real Madrid actuelle
            Team realMadrid = dataRetriever.findTeamById(1);
            if (realMadrid == null) {
                System.out.println("❌ ÉCHEC: Real Madrid non trouvée");
                return;
            }

            int initialPlayerCount = realMadrid.getPlayers().size();
            System.out.println("Real Madrid a actuellement " + initialPlayerCount + " joueurs");

            // Créer Vini avec référence à Real Madrid
            Player vini = findPlayerById(6);
            if (vini == null) {
                System.out.println("❌ ÉCHEC: Vini non trouvé");
                return;
            }

            vini.setTeam(realMadrid);

            // Ajouter Vini à la liste existante (ne pas remplacer)
            List<Player> updatedPlayers = new ArrayList<>(realMadrid.getPlayers());
            updatedPlayers.add(vini);
            realMadrid.setPlayers(updatedPlayers);

            System.out.println("Mise à jour de Real Madrid avec " + updatedPlayers.size() + " joueurs...");

            // Sauvegarder
            Team updatedTeam = dataRetriever.saveTeam(realMadrid);

            System.out.println("Résultat: Real Madrid a maintenant " +
                    updatedTeam.getPlayers().size() + " joueurs");

            if (updatedTeam.getPlayers().size() == initialPlayerCount + 1) {
                System.out.println("✓ SUCCÈS: Vini ajouté (+1 joueur)");

                // Vérifier que Vini est bien dans l'équipe
                boolean viniFound = false;
                for (Player p : updatedTeam.getPlayers()) {
                    if (p.getId() == 6) {
                        viniFound = true;
                        System.out.println("✓ Vini trouvé dans l'équipe: " + p.getName());
                        break;
                    }
                }

                if (!viniFound) {
                    System.out.println("❌ ÉCHEC: Vini n'est pas dans la liste des joueurs");
                }

                // Vérifier que les anciens joueurs sont toujours là
                System.out.println("Liste complète des joueurs:");
                for (Player p : updatedTeam.getPlayers()) {
                    System.out.println("  - " + p.getName() + " (ID: " + p.getId() + ")");
                }

            } else {
                System.out.println("❌ ÉCHEC: Nombre incorrect de joueurs");
            }

        } catch (Exception e) {
            System.out.println("❌ ERREUR: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void testJ() {
        System.out.println("Test j) saveTeam - Vider FC Barcelone");
        System.out.println("--------------------------------------");
        System.out.println("Scénario: Fournir une liste vide de joueurs à FC Barcelone");
        System.out.println("------------------------------------");

        try {
            // Récupérer FC Barcelone actuelle
            Team barcelona = dataRetriever.findTeamById(2);
            if (barcelona == null) {
                System.out.println("❌ ÉCHEC: FC Barcelone non trouvée");
                return;
            }

            int initialPlayerCount = barcelona.getPlayers().size();
            System.out.println("FC Barcelone a actuellement " + initialPlayerCount + " joueurs");

            if (initialPlayerCount > 0) {
                System.out.println("Joueurs actuels:");
                for (Player p : barcelona.getPlayers()) {
                    System.out.println("  - " + p.getName());
                }
            }

            // Vider la liste des joueurs
            barcelona.setPlayers(new ArrayList<>());

            System.out.println("Mise à jour avec liste vide...");

            // Sauvegarder
            Team updatedBarcelona = dataRetriever.saveTeam(barcelona);

            System.out.println("Résultat: FC Barcelone a maintenant " +
                    updatedBarcelona.getPlayers().size() + " joueurs");

            if (updatedBarcelona.getPlayers().isEmpty()) {
                System.out.println("✓ SUCCÈS: FC Barcelone vidée (0 joueur)");

                // Vérifier en base que les joueurs sont dissociés
                System.out.println("Vérification en base...");
                List<Player> playersAfter = getPlayersByTeamId(2);
                if (playersAfter.isEmpty()) {
                    System.out.println("✓ Confirmé: Aucun joueur n'est associé à FC Barcelone");
                } else {
                    System.out.println("⚠ ATTENTION: " + playersAfter.size() +
                            " joueurs sont encore associés en base");
                }

            } else {
                System.out.println("❌ ÉCHEC: La liste n'est pas vide");
            }

        } catch (Exception e) {
            System.out.println("❌ ERREUR: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    // ========== MÉTHODES UTILITAIRES ==========

    private static void deletePlayerIfExists(int playerId) {
        try {
            DBConnection db = new DBConnection();
            try (Connection conn = db.getDBConnection()) {
                String sql = "DELETE FROM player WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, playerId);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            // Ignorer si le joueur n'existe pas
        }
    }

    private static void ensureJudeBellinghamExists() {
        try {
            DBConnection db = new DBConnection();
            try (Connection conn = db.getDBConnection()) {
                // Vérifier s'il existe déjà
                String checkSql = "SELECT id FROM player WHERE id = 6";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    ResultSet rs = checkStmt.executeQuery();
                    if (!rs.next()) {
                        // Créer Jude Bellingham associé à Real Madrid
                        String insertSql = "INSERT INTO player (id, name, age, position, id_team) " +
                                "VALUES (6, 'Jude Bellingham', 23, 'MIDF'::enum_position, 1)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.executeUpdate();
                            System.out.println("  (Jude Bellingham créé pour le test)");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur création Jude Bellingham: " + e.getMessage());
        }
    }

    private static void ensureViniExists() {
        try {
            DBConnection db = new DBConnection();
            try (Connection conn = db.getDBConnection()) {
                // Vérifier s'il existe déjà
                String checkSql = "SELECT id FROM player WHERE id = 6";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    ResultSet rs = checkStmt.executeQuery();
                    if (!rs.next()) {
                        // Créer Vini sans équipe
                        String insertSql = "INSERT INTO player (id, name, age, position) " +
                                "VALUES (6, 'Vini', 25, 'STR'::enum_position)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.executeUpdate();
                            System.out.println("  (Vini créé pour le test)");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur création Vini: " + e.getMessage());
        }
    }

    private static boolean playerExists(int playerId) {
        try {
            DBConnection db = new DBConnection();
            try (Connection conn = db.getDBConnection()) {
                String sql = "SELECT id FROM player WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, playerId);
                    ResultSet rs = stmt.executeQuery();
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }

    private static Player findPlayerById(int playerId) {
        try {
            DBConnection db = new DBConnection();
            try (Connection conn = db.getDBConnection()) {
                String sql = "SELECT id, name, age, position, id_team FROM player WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, playerId);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        Player player = new Player();
                        player.setId(rs.getInt("id"));
                        player.setName(rs.getString("name"));
                        player.setAge(rs.getInt("age"));
                        player.setPosition(PlayerPositionEnum.valueOf(rs.getString("position")));

                        // Créer une équipe minimale si id_team existe
                        Integer teamId = rs.getObject("id_team", Integer.class);
                        if (teamId != null) {
                            Team team = new Team();
                            team.setId(teamId);
                            player.setTeam(team);
                        }

                        return player;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Player> getPlayersByTeamId(int teamId) {
        List<Player> players = new ArrayList<>();
        try {
            DBConnection db = new DBConnection();
            try (Connection conn = db.getDBConnection()) {
                String sql = "SELECT id, name, age, position FROM player WHERE id_team = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, teamId);
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        Player player = new Player();
                        player.setId(rs.getInt("id"));
                        player.setName(rs.getString("name"));
                        player.setAge(rs.getInt("age"));
                        player.setPosition(PlayerPositionEnum.valueOf(rs.getString("position")));
                        players.add(player);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    private static void resetRealMadrid() {
        try {
            DBConnection db = new DBConnection();
            try (Connection conn = db.getDBConnection()) {
                // Dissocier tous les joueurs de Real Madrid
                String sql = "UPDATE player SET id_team = NULL WHERE id_team = 1";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.executeUpdate();
                }

                // Réassocier les 3 joueurs d'origine (selon les données du sujet)
                // Note: Ajustez les IDs selon votre base
                String[] updateQueries = {
                        "UPDATE player SET id_team = 1 WHERE id = 1", // Thibaut Courtois
                        "UPDATE player SET id_team = 1 WHERE id = 2", // Dani Carvajal
                        "UPDATE player SET id_team = 1 WHERE id = 3"  // Jude Bellingham
                };

                for (String query : updateQueries) {
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            // Ignorer les erreurs
        }
    }

    private static void resetBarcelona() {
        try {
            DBConnection db = new DBConnection();
            try (Connection conn = db.getDBConnection()) {
                // Réassocier les joueurs d'origine à Barcelone
                // Note: Ajustez les IDs selon votre base
                String[] updateQueries = {
                        "UPDATE player SET id_team = 2 WHERE id = 4", // Exemple: premier joueur
                        "UPDATE player SET id_team = 2 WHERE id = 5"  // Exemple: deuxième joueur
                };

                for (String query : updateQueries) {
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            // Ignorer les erreurs
        }
    }
}
