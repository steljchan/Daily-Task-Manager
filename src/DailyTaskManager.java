import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Colors {
    public static final String RESET = "\u001B[0m";     
    public static final String RED = "\u001B[31m";      
    public static final String GREEN = "\u001B[32m";   
    public static final String ORANGE = "\u001B[33m"; 
    public static final String YELLOW = "\u001B[33m";   
    public static final String BLUE = "\u001B[34m";  
    public static final String PINK = "\u001B[35m";   
    public static final String BG_WHITE = "\u001B[47m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_GREEN = "\u001B[42m";}

// Node for Linked List
class Node {
    String task;
    LocalDate deadline;
    int priority;
    Node next;

    Node(String task, LocalDate deadline, int priority) {
        this.task = task;
        this.deadline = deadline;
        this.priority = priority;
        this.next = null;
    }
}

// Main class
public class DailyTaskManager {
    static String username;
    static String[] tasks = {"Check email", "Attend lecture", "Exercise", "Read a book", "Practice coding"};
    static Stack<String> undoStack = new Stack<>();
    static Node head = null;
    static Stack<Node> deletedTasksStack = new Stack<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print(Colors.BLUE + "Enter your name: " + Colors.RESET);
        username = scanner.nextLine();
        displayCatArt2();
        greetUser();
        int choice;
        do {
            displayMenu();
            System.out.print(Colors.BG_BLUE + "Enter your choice: " + Colors.RESET);
            while (!scanner.hasNextInt()) {
                System.out.println(Colors.RED + "Please enter a valid number!" + Colors.RESET);
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> displayTaskArray();
                case 2 -> updateTask();
                case 3 -> markTaskAsCompleted();
                case 4 -> undoCompletedTask();
                case 5 -> addTaskLinkedList();
                case 6 -> deleteTaskLinkedList();
                case 7 -> undoDeleteTask ();
                case 8 -> displayTaskLinkedList();
                case 9 -> searchTask();
                case 10 -> sortTasks();
                case 11 -> {
                    System.out.println(Colors.PINK + "Program ended. Goodbye!" + Colors.RESET);
                    displayCatArt(); 
                }

                default -> System.out.println(Colors.RED + "Invalid choice. Try again." + Colors.RESET);
            }
        } while (choice != 10);
    }

    static void displayCatArt2() {
        System.out.println(Colors.GREEN + "    |\\__/,|   (`\\");
        System.out.println("  _.|o o  |_   ) )");
        System.out.println(" -(((---(((--------" +Colors.RESET );}

    static void displayCatArt() {
        System.out.println(Colors.GREEN + "    /\\_____/\\");
        System.out.println("   /  o   o  \\");
        System.out.println("  ( ==  ^  == )");
        System.out.println("   )         (");
        System.out.println("  (           )");
        System.out.println(" ( (  )   (  ) )");
        System.out.println("(__(__)___(__)__)" + Colors.RESET);
    }

    static void greetUser() {
        LocalTime now = LocalTime.now();
        String greeting;
    
        if (now.isBefore(LocalTime.NOON)) {
            greeting = "Good morning";
        } else if (now.isBefore(LocalTime.of(18, 0))) {
            greeting = "Good afternoon";
        } else {
            greeting = "Good evening";
        }
    
        System.out.println(Colors.GREEN + Colors.BG_WHITE+ greeting + ", " + username + "!" + Colors.RESET);
    }

    static void displayMenu() {
        System.out.println(Colors.BG_BLUE + "=== Daily Task Manager ===" + Colors.RESET);
        System.out.println(Colors.YELLOW + "1. View task list" + Colors.RESET);
        System.out.println(Colors.YELLOW + "2. Update task" + Colors.RESET);
        System.out.println(Colors.YELLOW + "3. Mark task as completed" + Colors.RESET);
        System.out.println(Colors.YELLOW + "4. Undo completed task" + Colors.RESET);
        System.out.println(Colors.YELLOW + "5. Add task to dynamic list" + Colors.RESET);
        System.out.println(Colors.YELLOW + "6. Delete task from dynamic list" + Colors.RESET);
        System.out.println(Colors.YELLOW + "7. Undo deleted task" + Colors.RESET);
        System.out.println(Colors.YELLOW + "8. Display all tasks in dynamic list" + Colors.RESET);
        System.out.println(Colors.YELLOW + "9. Search tasks" + Colors.RESET);
        System.out.println(Colors.YELLOW + "10. Sort tasks by priority or deadline" + Colors.RESET);
        System.out.println(Colors.RED + "11. Exit" + Colors.RESET);
    }
    

    static void searchTask() {
        System.out.print(Colors.BLUE + "Enter keyword to search: " + Colors.RESET);
        String keyword = scanner.nextLine().toLowerCase();
        Node temp = head;
        boolean found = false;

        while (temp != null) {
            if (temp.task.toLowerCase().contains(keyword)) {
                System.out.println("Found: " + temp.task);
                found = true;
            }
            temp = temp.next;
        }

        if (!found) {
            System.out.println(Colors.RED + "No tasks found with that keyword." + Colors.RESET);
        }
    }

    static void sortTasks() {
        List<Node> taskList = new ArrayList<>();
        Node temp = head;
        while (temp != null) {
            taskList.add(temp);
            temp = temp.next;
        }

        taskList.sort(Comparator.comparing((Node n) -> n.deadline).thenComparingInt(n -> n.priority));

        System.out.println("\nSorted Tasks:");
        for (Node task : taskList) {
            System.out.println(task.task + " | Deadline: " + task.deadline + " | Priority: " + task.priority);
        }
    }

    static String getDeadlineStatus(LocalDate deadline) {
        LocalDateTime now = LocalDateTime.now();
        long daysLeft = ChronoUnit.DAYS.between(now, deadline.atStartOfDay());
        
        if (daysLeft < 0) return Colors.RED + "(Terlambat)" + Colors.RESET;
        if (daysLeft == 0 || daysLeft == 1) return Colors.ORANGE + "(Hampir Tenggat)" + Colors.RESET;
        if (daysLeft <= 3) return Colors.YELLOW + "(Segera)" + Colors.RESET;
        return Colors.GREEN + "(Tepat Waktu)" + Colors.RESET;
    }

    static void displayTaskArray() {
        System.out.println(Colors.GREEN + "\nTask List for " + username + ":" + Colors.RESET);
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(Colors.RED + (i + 1) + ". " + (tasks[i] != null ? tasks[i] + " (by " + username + ")" : "(Empty)") + Colors.RESET);
        }
    }
    
    static void updateTask() {
        displayTaskArray();
        System.out.print(Colors.BLUE + "Enter the task number to update: " + Colors.RESET);
        int index = scanner.nextInt();
        scanner.nextLine();
    
        if (index < 1 || index > tasks.length) {
            System.out.println(Colors.RED + "Invalid task number." + Colors.RESET);
            return;
        }
    
        System.out.print(Colors.BG_BLUE + "Update Task: " + Colors.RESET);
        tasks[index - 1] = scanner.nextLine() + " (by " + username + ")";
        System.out.println(Colors.GREEN + "Task updated successfully!" + Colors.RESET);
    }
    
    static void markTaskAsCompleted() {
        displayTaskArray();
        System.out.print(Colors.BLUE + "Enter the task number to mark as completed: " + Colors.RESET);
        int index = scanner.nextInt();
        scanner.nextLine();
    
        if (index < 1 || index > tasks.length || tasks[index - 1] == null) {
            System.out.println(Colors.RED + "Invalid task number." + Colors.RESET);
            return;
        }
    
        undoStack.push(tasks[index - 1]);
        tasks[index - 1] = null;
        System.out.println(Colors.YELLOW + "Task marked as completed and moved to the undo stack." + Colors.RESET);
    }
    
    static void undoCompletedTask() {
        if (undoStack.isEmpty()) {
            System.out.println(Colors.RED + "No tasks to undo." + Colors.RESET);
            return;
        }
    
        String undoneTask = undoStack.pop();
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                tasks[i] = undoneTask;
                System.out.println(Colors.BG_BLUE + "Task restored: " + undoneTask + Colors.RESET);
                return;
            }
        }
    
        System.out.println(Colors.RED + "Task could not be restored. Task list may be full." + Colors.RESET);
    }
    
    static void addTaskLinkedList() {
        System.out.print(Colors.BLUE + "Enter task: " );
        String task = scanner.nextLine();
        System.out.print("Enter deadline (YYYY-MM-DD): ");
        LocalDate deadline = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter priority (1-5): " + Colors.RESET);
        int priority = scanner.nextInt();
        scanner.nextLine();
    
        Node newNode = new Node(task, deadline, priority);
    
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    
        System.out.println(Colors.GREEN + "Task added successfully." + Colors.RESET);
    }
    
    static void deleteTaskLinkedList() {
        if (head == null) {
            System.out.println(Colors.RED + "No tasks to delete." + Colors.RESET);
            return;
        }
    
        System.out.print(Colors.BLUE + "Enter task to delete: " + Colors.RESET);
        String taskToDelete = scanner.nextLine();
    
        Node temp = head;
        Node prev = null;
    
        while (temp != null && !temp.task.equalsIgnoreCase(taskToDelete)) {
            prev = temp;
            temp = temp.next;
        }
    
        if (temp == null) {
            System.out.println(Colors.RED + "Task not found." + Colors.RESET);
        } else {
            if (prev == null) {
                head = temp.next;
            } else {
                prev.next = temp.next;
            }
    
            // Menyimpan task yang dihapus ke Stack untuk fitur undo
            deletedTasksStack.push(temp);
    
            System.out.println(Colors.BG_GREEN + "Task '" + taskToDelete + "' has been deleted." + Colors.RESET);
        }
    }    

    static void undoDeleteTask() {
        if (deletedTasksStack.isEmpty()) {
            System.out.println(Colors.RED + "No tasks to undo." + Colors.RESET);
            return;
        }
    
        Node restoredTask = deletedTasksStack.pop();
    
        // Menambah task yang dikembalikan ke awal linked list
        restoredTask.next = head;
        head = restoredTask;
    
        System.out.println(Colors.BG_GREEN + "Task '" + restoredTask.task + "' has been restored." + Colors.RESET);
    }
    
    
    static void displayTaskLinkedList() {
        if (head == null) {
            System.out.println(Colors.RED + "Task list is empty." + Colors.RESET);
            return;
        }
        Node temp = head;
        System.out.println(Colors.BG_BLUE + "\nDynamic Task List:" + Colors.RESET);
        while (temp != null) {
            System.out.println(Colors.GREEN + temp.task + " | Deadline: " 
                + temp.deadline + " | Priority: " + temp.priority + Colors.RESET);
            temp = temp.next;
        }
        
    }
}

    
