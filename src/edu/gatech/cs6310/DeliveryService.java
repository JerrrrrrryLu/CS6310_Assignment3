package edu.gatech.cs6310;

import java.util.*;

public class DeliveryService {
    // Global variable
    TreeMap<String, Store> store_ls;
    TreeMap<String, Pilot> pilot_ls;
    TreeMap<String, Customer> customer_ls;
    Set<String> licenses;
    // constants variable
    final String DELIMITER = ",";

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;

        store_ls = new TreeMap<String, Store>();
        pilot_ls = new TreeMap<String, Pilot>();
        customer_ls = new TreeMap<String, Customer>();
        licenses = new HashSet<String>();

        label:
        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);
                if (wholeInputLine.startsWith("//")) {
                    continue;
                }
                String command = tokens[0];

                switch (command) {
                    case "make_store":

                        if (store_ls.containsKey(tokens[1])) {
                            System.out.println("ERROR:store_identifier_already_exists");
                        } else {
                            int revenue = Integer.parseInt(tokens[2]);
                            Store s = new Store(tokens[1], revenue);
                            store_ls.put(tokens[1], s);
                            System.out.println("OK:change_completed");
                        }

                        break;
                    case "display_stores":
                        for (Map.Entry<String, Store> entry : store_ls.entrySet()) {
                            entry.getValue().display();
                        }
                        System.out.println("OK:display_completed");

                        break;
                    case "sell_item":
                        //System.out.println("store: " + tokens[1] + ", item: " + tokens[2] + ", weight: " + tokens[3]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            if (s.item_list.containsKey(tokens[2])) {
                                System.out.println("ERROR:item_identifier_already_exists");
                            } else {
                                s.addItem(tokens[2], tokens[3]);
                                System.out.println("OK:change_completed");
                            }
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                        break;
                    case "display_items":
                        //System.out.println("store: " + tokens[1]);

                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            s.displayItems();
                            System.out.println("OK:display_completed");
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                        break;
                    case "make_pilot":
                        if (pilot_ls.containsKey(tokens[1])) {
                            System.out.println("ERROR:pilot_identifier_already_exists");
                        } else {
                            if (licenses.contains(tokens[6])) {
                                System.out.println("ERROR:pilot_license_already_exists");
                            } else {
                                Pilot p = new Pilot(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6],
                                        Integer.parseInt(tokens[7]));
                                pilot_ls.put(tokens[1], p);
                                licenses.add(tokens[6]);
                                System.out.println("OK:change_completed");
                            }
                        }
                        break;
                    case "display_pilots":
                        for (Map.Entry<String, Pilot> entry : pilot_ls.entrySet()) {
                            entry.getValue().display();
                        }
                        System.out.println("OK:display_completed");

                        break;
                    case "make_drone":
                        //System.out.println("store: " + tokens[1] + ", drone: " + tokens[2] + ", capacity: " + tokens[3] + ", fuel: " + tokens[4]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            if (s.drone_list.containsKey(tokens[2])) {
                                System.out.println("ERROR:drone_identifier_already_exists");
                            } else {
                                s.addDrone(tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                                System.out.println("OK:change_completed");
                            }
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                        break;
                    case "display_drones":
                        //System.out.println("store: " + tokens[1]);

                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            s.displayDrones();
                            System.out.println("OK:display_completed");
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                        break;
                    case "fly_drone":
                        //System.out.println("store: " + tokens[1] + ", drone: " + tokens[2] + ", pilot: " + tokens[3]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            if (s.drone_list.containsKey(tokens[2])) {
                                if (pilot_ls.containsKey(tokens[3])) {
                                    Pilot p = pilot_ls.get(tokens[3]);
                                    Drone d = s.drone_list.get(tokens[2]);

                                    if (d.pilot != null) {
                                        Pilot p_2 = d.pilot;
                                        p_2.drone = null;
                                    }

                                    if (p.drone != null) {
                                        Drone d_2 = p.drone;
                                        d_2.pilot = null;
                                    }

                                    d.pilot = p;
                                    p.drone = d;

                                    System.out.println("OK:change_completed");
                                } else {
                                    System.out.println("ERROR:pilot_identifier_does_not_exist");
                                }

                            } else {
                                System.out.println("ERROR:drone_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                        break;
                    case "make_customer":
                        if (customer_ls.containsKey(tokens[1])) {
                            System.out.println("ERROR:customer_identifier_already_exists");
                        } else {
                            Customer c = new Customer(tokens[1], tokens[2], tokens[3], tokens[4],
                                    Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                            customer_ls.put(tokens[1], c);
                            System.out.println("OK:change_completed");
                        }

                        break;
                    case "display_customers":
                        for (Map.Entry<String, Customer> entry : customer_ls.entrySet()) {
                            entry.getValue().display();
                        }
                        System.out.println("OK:display_completed");

                        break;
                    case "start_order":
                        //System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", drone: " + tokens[3] + ", customer: " + tokens[4]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            if (s.order_list.containsKey(tokens[2])) {
                                System.out.println("ERROR:order_identifier_already_exists");
                            } else {
                                if (s.drone_list.containsKey(tokens[3])) {
                                    if (customer_ls.containsKey(tokens[4])) {
                                        Customer c = customer_ls.get(tokens[4]);
                                        Drone d = s.drone_list.get(tokens[3]);

                                        Order o = new Order(tokens[1], tokens[2], tokens[3], tokens[4], c.credit, d.capacity);

                                        s.order_list.put(tokens[2], o);
                                        c.order_list.put(tokens[2], o);
                                        d.order_list.put(tokens[2], o);

                                        System.out.println("OK:change_completed");

                                    } else {
                                        System.out.println("ERROR:customer_identifier_does_not_exist");
                                    }
                                } else {
                                    System.out.println("ERROR:drone_identifier_does_not_exist");
                                }
                            }
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                        break;
                    case "display_orders":
                        //System.out.println("store: " + tokens[1]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            s.displayOrder();
                            System.out.println("OK:display_completed");
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }
                        break;
                    case "request_item":
                        //System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", item: " + tokens[3] + ", quantity: " + tokens[4] + ", unit_price: " + tokens[5]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            if (s.order_list.containsKey(tokens[2])) {
                                if (s.item_list.containsKey(tokens[3])) {
                                    Order o = s.order_list.get(tokens[2]);
                                    if (o.order_detail.containsKey(tokens[3])) {
                                        System.out.println("ERROR:item_already_ordered");
                                    } else {
                                        Customer c = customer_ls.get(s.order_list.get(tokens[2]).customerId);
                                        s.requestItem(tokens[2], tokens[3], Integer.parseInt(tokens[4]),
                                                Integer.parseInt(tokens[5]), c);
                                    }
                                } else {
                                    System.out.println("ERROR:item_identifier_does_not_exist");
                                }
                            } else {
                                System.out.println("ERROR:order_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                        break;
                    case "purchase_order":
                        //System.out.println("store: " + tokens[1] + ", order: " + tokens[2]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            if (s.order_list.containsKey(tokens[2])) {
                                Customer c = customer_ls.get(s.order_list.get(tokens[2]).customerId);
                                s.complete_order(tokens[2], c);
                            } else {
                                System.out.println("ERROR:order_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }
                        break;
                    case "cancel_order":
                        //System.out.println("store: " + tokens[1] + ", order: " + tokens[2]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            if (s.order_list.containsKey(tokens[2])) {
                                Order o = s.order_list.get(tokens[2]);
                                Drone d = s.drone_list.get(o.droneId);
                                Customer c = customer_ls.get(o.customerId);

                                d.remaining_cap += o.total_weight;

                                s.order_list.remove(tokens[2]);
                                c.order_list.remove(tokens[2]);
                                s.drone_list.get(o.droneId).order_list.remove(tokens[2]);
                                System.out.println("OK:change_completed");
                            } else {
                                System.out.println("ERROR:order_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                        break;
                    case "transfer_order":
//                        System.out.println("store: " + tokens[1] + ", order: " + tokens[2] + ", drone:" + tokens[3]);
                        if (store_ls.containsKey(tokens[1])) {
                            Store s = store_ls.get(tokens[1]);
                            if (s.order_list.containsKey(tokens[2])) {
                                Order o = s.order_list.get(tokens[2]);
                                String old_drone_id = o.droneId;
                                Drone d = s.drone_list.get(old_drone_id);
                                if (old_drone_id.equals(tokens[3])){
                                    System.out.println("OK:new_drone_is_current_drone_no_change");
                                } else {
                                    d.remaining_cap += o.total_weight;
                                    d.order_list.remove(tokens[2]);
                                    o.droneId = tokens[3];
                                    s.order_transferred +=1;
                                    System.out.println("OK:change_completed");
                                }
                            } else {
                                System.out.println("ERROR:order_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }
                        break;

                    case "display_efficiency":
                        for (Map.Entry<String, Store> entry : store_ls.entrySet()) {
                            Store s = entry.getValue();
                            s.displayEfficiency();
                        }
                        System.out.println("OK:display_completed");
                        break;
                    case "stop":
                        System.out.println("stop acknowledged");
                        break label;

                    default:
                        System.out.println("command " + tokens[0] + " NOT acknowledged");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }


}