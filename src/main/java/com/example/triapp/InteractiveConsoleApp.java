package com.example.triapp;

import com.example.triapp.data.DataProvider;
import com.example.triapp.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * InteractiveConsoleApp - simple console UI for manual testing of CRUD operations.
 *
 * Commands:
 *  help                     - show this help
 *  add-user                 - interactively add a User
 *  del-user <id>            - delete user by id
 *  list-users               - list all users
 *
 *  add-workout              - add a Workout (asks for userId and basic fields)
 *  del-workout <id>         - delete workout by id
 *  list-workouts            - list all workouts
 *
 *  add-run                  - create a RUN workout + run details
 *  add-bike                 - create a BIKE workout + bike details
 *  add-swim                 - create a SWIM workout + swim details
 *
 *  del-run <id>             - delete run row by run id
 *  del-bike <id>            - delete bike row by bike id
 *  del-swim <id>            - delete swim row by swim id
 *
 *  list-runs/list-bikes/list-swims  - list specialized rows
 *
 *  exit                     - quit the console
 *
 * Notes:
 *  - DataProvider throws SQLExceptions; this console prints errors to help debugging.
 */
@Component
public class InteractiveConsoleApp implements CommandLineRunner {

    private final DataProvider dataProvider;

    public InteractiveConsoleApp(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("=== TRIAPP INTERACTIVE CONSOLE ===");
            printHelp();

            while (true) {
                System.out.print("\ntriapp> ");
                String line = sc.nextLine();
                if (line == null) break;
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 0 || parts[0].isEmpty()) continue;

                String cmd = parts[0].toLowerCase();
                try {
                    switch (cmd) {
                        case "help":
                            printHelp();
                            break;

                        case "add-user":
                            doAddUser(sc);
                            break;

                        case "del-user":
                            if (parts.length < 2) {
                                System.out.println("Usage: del-user <id>");
                            } else {
                                long uid = Long.parseLong(parts[1]);
                                boolean ok = dataProvider.deleteUser(uid);
                                System.out.println(ok ? "User deleted." : "User not found / not deleted.");
                            }
                            break;

                        case "list-users":
                            List<User> users = dataProvider.readAllUsers();
                            if (users.isEmpty()) System.out.println("(no users)");
                            users.forEach(u -> System.out.println(u));
                            break;

                        case "add-workout":
                            doAddWorkout(sc);
                            break;

                        case "del-workout":
                            if (parts.length < 2) {
                                System.out.println("Usage: del-workout <id>");
                            } else {
                                long wid = Long.parseLong(parts[1]);
                                boolean ok = dataProvider.deleteWorkout(wid);
                                System.out.println(ok ? "Workout deleted." : "Workout not found / not deleted.");
                            }
                            break;

                        case "list-workouts":
                            List<Workout> wlist = dataProvider.readAllWorkouts();
                            if (wlist.isEmpty()) System.out.println("(no workouts)");
                            wlist.forEach(w -> System.out.println(w));
                            break;

                        case "add-run":
                            doAddRun(sc);
                            break;

                        case "add-bike":
                            doAddBike(sc);
                            break;

                        case "add-swim":
                            doAddSwim(sc);
                            break;

                        case "del-run":
                            if (parts.length < 2) {
                                System.out.println("Usage: del-run <id>");
                            } else {
                                long id = Long.parseLong(parts[1]);
                                boolean ok = dataProvider.deleteRun(id);
                                System.out.println(ok ? "Run deleted." : "Run not found / not deleted.");
                            }
                            break;

                        case "del-bike":
                            if (parts.length < 2) {
                                System.out.println("Usage: del-bike <id>");
                            } else {
                                long id = Long.parseLong(parts[1]);
                                boolean ok = dataProvider.deleteBike(id);
                                System.out.println(ok ? "Bike deleted." : "Bike not found / not deleted.");
                            }
                            break;

                        case "del-swim":
                            if (parts.length < 2) {
                                System.out.println("Usage: del-swim <id>");
                            } else {
                                long id = Long.parseLong(parts[1]);
                                boolean ok = dataProvider.deleteSwim(id);
                                System.out.println(ok ? "Swim deleted." : "Swim not found / not deleted.");
                            }
                            break;

                        case "list-runs":
                            List<Run> runs = dataProvider.readAllRuns();
                            if (runs.isEmpty()) System.out.println("(no runs)");
                            runs.forEach(r -> System.out.println(r));
                            break;

                        case "list-bikes":
                            List<Bike> bikes = dataProvider.readAllBikes();
                            if (bikes.isEmpty()) System.out.println("(no bikes)");
                            bikes.forEach(b -> System.out.println(b));
                            break;

                        case "list-swims":
                            List<Swim> swims = dataProvider.readAllSwims();
                            if (swims.isEmpty()) System.out.println("(no swims)");
                            swims.forEach(s -> System.out.println(s));
                            break;

                        case "exit":
                            System.out.println("Exiting interactive console.");
                            return;

                        default:
                            System.out.println("Unknown command. Type 'help' for list of commands.");
                    }
                } catch (Exception ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        }
    }

    private void printHelp() {
        System.out.println("Commands: help, add-user, del-user <id>, list-users,");
        System.out.println("          add-workout, del-workout <id>, list-workouts,");
        System.out.println("          add-run, add-bike, add-swim,");
        System.out.println("          del-run <id>, del-bike <id>, del-swim <id>,");
        System.out.println("          list-runs, list-bikes, list-swims, exit");
    }

    private void doAddUser(Scanner sc) throws Exception {
        System.out.println("--- Add User ---");
        System.out.print("First name: ");
        String first = sc.nextLine().trim();
        System.out.print("Last name: ");
        String last = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password (plain for test): ");
        String pw = sc.nextLine().trim();
        System.out.print("Height (inches) [or blank]: ");
        String hStr = sc.nextLine().trim();
        Integer h = hStr.isEmpty() ? null : Integer.parseInt(hStr);
        System.out.print("Weight (lbs) [or blank]: ");
        String wStr = sc.nextLine().trim();
        Double w = wStr.isEmpty() ? null : Double.parseDouble(wStr);

        User u = new User();
        u.setFirstName(first);
        u.setLastName(last);
        u.setEmail(email);
        u.setPassword(pw);
        u.setHeightInches(h);
        u.setWeightPounds(w);

        long id = dataProvider.createUser(u);
        System.out.println("User created with id: " + id);
    }

    private void doAddWorkout(Scanner sc) throws Exception {
        System.out.println("--- Add Workout ---");
        System.out.print("User id: ");
        long uid = Long.parseLong(sc.nextLine().trim());
        System.out.print("Type (RUN/BIKE/SWIM): ");
        String type = sc.nextLine().trim().toUpperCase();
        if (!type.equals("RUN") && !type.equals("BIKE") && !type.equals("SWIM")) {
            System.out.println("Invalid type. Aborting.");
            return;
        }
        System.out.print("Moving time seconds [or blank]: ");
        String mtStr = sc.nextLine().trim();
        Integer mt = mtStr.isEmpty() ? null : Integer.parseInt(mtStr);
        System.out.print("Elapsed time seconds [or blank]: ");
        String etStr = sc.nextLine().trim();
        Integer et = etStr.isEmpty() ? null : Integer.parseInt(etStr);
        System.out.print("Avg heart rate [or blank]: ");
        String hrStr = sc.nextLine().trim();
        Integer hr = hrStr.isEmpty() ? null : Integer.parseInt(hrStr);
        System.out.print("Calories [or blank]: ");
        String cStr = sc.nextLine().trim();
        Integer cal = cStr.isEmpty() ? null : Integer.parseInt(cStr);

        Workout w = new Workout();
        w.setUserId(uid);
        w.setWorkoutType(type);
        w.setDate(LocalDateTime.now());
        w.setMovingTimeSec(mt);
        w.setElapsedTimeSec(et);
        w.setAvgHeartRate(hr);
        w.setCalories(cal);

        long wid = dataProvider.createWorkout(w);
        System.out.println("Workout created id: " + wid);
    }

    private void doAddRun(Scanner sc) throws Exception {
        System.out.println("--- Add RUN (creates workout + run row) ---");
        System.out.print("User id: ");
        long uid = Long.parseLong(sc.nextLine().trim());

        // Create workout first
        Workout w = new Workout();
        w.setUserId(uid);
        w.setWorkoutType("RUN");
        w.setDate(LocalDateTime.now());
        System.out.print("Moving time seconds [or blank]: ");
        String m = sc.nextLine().trim();
        w.setMovingTimeSec(m.isEmpty() ? null : Integer.parseInt(m));
        System.out.print("Calories [or blank]: ");
        String c = sc.nextLine().trim();
        w.setCalories(c.isEmpty() ? null : Integer.parseInt(c));
        long wid = dataProvider.createWorkout(w);
        System.out.println("Workout created id: " + wid);

        // Run row
        System.out.print("Distance (miles) [e.g. 5.0]: ");
        String distStr = sc.nextLine().trim();
        Double dist = distStr.isEmpty() ? null : Double.parseDouble(distStr);
        System.out.print("Avg pace sec/mile [e.g. 600]: ");
        String paceStr = sc.nextLine().trim();
        Integer pace = paceStr.isEmpty() ? null : Integer.parseInt(paceStr);
        System.out.print("Elevation gain feet [or blank]: ");
        String eg = sc.nextLine().trim();
        Integer elev = eg.isEmpty() ? null : Integer.parseInt(eg);
        System.out.print("Steps [or blank]: ");
        String steps = sc.nextLine().trim();
        Integer st = steps.isEmpty() ? null : Integer.parseInt(steps);
        System.out.print("Avg cadence spm [or blank]: ");
        String cad = sc.nextLine().trim();
        Integer cs = cad.isEmpty() ? null : Integer.parseInt(cad);

        Run r = new Run();
        r.setWorkoutId(wid);
        r.setDistanceMiles(dist);
        r.setAvgPaceSecPerMile(pace);
        r.setElevationGainFeet(elev);
        r.setSteps(st);
        r.setAvgCadenceSpm(cs);

        long rid = dataProvider.createRun(r);
        System.out.println("Run created id: " + rid);
    }

    private void doAddBike(Scanner sc) throws Exception {
        System.out.println("--- Add BIKE (creates workout + bike row) ---");
        System.out.print("User id: ");
        long uid = Long.parseLong(sc.nextLine().trim());

        Workout w = new Workout();
        w.setUserId(uid);
        w.setWorkoutType("BIKE");
        w.setDate(LocalDateTime.now());
        System.out.print("Moving time seconds [or blank]: ");
        String m = sc.nextLine().trim();
        w.setMovingTimeSec(m.isEmpty() ? null : Integer.parseInt(m));
        System.out.print("Calories [or blank]: ");
        String c = sc.nextLine().trim();
        w.setCalories(c.isEmpty() ? null : Integer.parseInt(c));
        long wid = dataProvider.createWorkout(w);
        System.out.println("Workout created id: " + wid);

        System.out.print("Distance (miles) [or blank]: ");
        String distStr = sc.nextLine().trim();
        Double dist = distStr.isEmpty() ? null : Double.parseDouble(distStr);
        System.out.print("Avg speed mph [or blank]: ");
        String avgStr = sc.nextLine().trim();
        Double avg = avgStr.isEmpty() ? null : Double.parseDouble(avgStr);
        System.out.print("Max speed mph [or blank]: ");
        String maxStr = sc.nextLine().trim();
        Double max = maxStr.isEmpty() ? null : Double.parseDouble(maxStr);
        System.out.print("Elevation gain feet [or blank]: ");
        String eg = sc.nextLine().trim();
        Integer elev = eg.isEmpty() ? null : Integer.parseInt(eg);
        System.out.print("Avg pedal rate rpm [or blank]: ");
        String rpmStr = sc.nextLine().trim();
        Integer rpm = rpmStr.isEmpty() ? null : Integer.parseInt(rpmStr);

        Bike b = new Bike();
        b.setWorkoutId(wid);
        b.setDistanceMiles(dist);
        b.setAvgSpeedMph(avg);
        b.setMaxSpeedMph(max);
        b.setElevationGainFeet(elev);
        b.setAvgPedalRateRpm(rpm);

        long bid = dataProvider.createBike(b);
        System.out.println("Bike created id: " + bid);
    }

    private void doAddSwim(Scanner sc) throws Exception {
        System.out.println("--- Add SWIM (creates workout + swim row) ---");
        System.out.print("User id: ");
        long uid = Long.parseLong(sc.nextLine().trim());

        Workout w = new Workout();
        w.setUserId(uid);
        w.setWorkoutType("SWIM");
        w.setDate(LocalDateTime.now());
        System.out.print("Moving time seconds [or blank]: ");
        String m = sc.nextLine().trim();
        w.setMovingTimeSec(m.isEmpty() ? null : Integer.parseInt(m));
        System.out.print("Calories [or blank]: ");
        String c = sc.nextLine().trim();
        w.setCalories(c.isEmpty() ? null : Integer.parseInt(c));
        long wid = dataProvider.createWorkout(w);
        System.out.println("Workout created id: " + wid);

        System.out.print("Distance (yards) [or blank]: ");
        String distStr = sc.nextLine().trim();
        Integer dist = distStr.isEmpty() ? null : Integer.parseInt(distStr);
        System.out.print("Avg pace sec/100y [or blank]: ");
        String paceStr = sc.nextLine().trim();
        Integer pace = paceStr.isEmpty() ? null : Integer.parseInt(paceStr);
        System.out.print("Avg stroke rate spm [or blank]: ");
        String srStr = sc.nextLine().trim();
        Integer sr = srStr.isEmpty() ? null : Integer.parseInt(srStr);

        Swim s = new Swim();
        s.setWorkoutId(wid);
        s.setDistanceYards(dist);
        s.setAvgPaceSecPer100Y(pace);
        s.setAvgStrokeRateSpm(sr);

        long sid = dataProvider.createSwim(s);
        System.out.println("Swim created id: " + sid);
    }
}