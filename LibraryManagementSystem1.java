import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Book {
    int id;
    String title, author, publisher;
    public Book(int id, String title, String author, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }
}

public class LibraryManagementSystem1 extends JFrame {
    private ArrayList<Book> books = new ArrayList<>();
    private JTextField idField, titleField, authorField, publisherField, searchField;
    private JTable bookTable;
    private DefaultTableModel model;

    public LibraryManagementSystem1() {
        setTitle("Library Management System1");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- UI Components ---
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Book Details"));

        panel.add(new JLabel("Book ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Title:"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("Publisher:"));
        publisherField = new JTextField();
        panel.add(publisherField);

        JButton addBtn = new JButton("Add Book");
        JButton updateBtn = new JButton("Update Book");
        JButton deleteBtn = new JButton("Delete Book");
        JButton clearBtn = new JButton("Clear Fields");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(clearBtn);

        // --- Table for Books ---
        String[] columns = {"ID", "Title", "Author", "Publisher"};
        model = new DefaultTableModel(columns, 0);
        bookTable = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(bookTable);

        // --- Search Bar ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        // --- Layout ---
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---
        addBtn.addActionListener(e -> addBook());
        updateBtn.addActionListener(e -> updateBook());
        deleteBtn.addActionListener(e -> deleteBook());
        clearBtn.addActionListener(e -> clearFields());
        searchBtn.addActionListener(e -> searchBook());

        // Fill fields when row clicked
        bookTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = bookTable.getSelectedRow();
                if (row != -1) {
                    idField.setText(model.getValueAt(row, 0).toString());
                    titleField.setText(model.getValueAt(row, 1).toString());
                    authorField.setText(model.getValueAt(row, 2).toString());
                    publisherField.setText(model.getValueAt(row, 3).toString());
                }
            }
        });
    }

    // --- Functionalities ---
    private void addBook() {
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String author = authorField.getText();
            String publisher = publisherField.getText();

            if (title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            books.add(new Book(id, title, author, publisher));
            model.addRow(new Object[]{id, title, author, publisher});
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!");
        }
    }

    private void updateBook() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to update!");
            return;
        }
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String author = authorField.getText();
            String publisher = publisherField.getText();

            books.set(row, new Book(id, title, author, publisher));
            model.setValueAt(id, row, 0);
            model.setValueAt(title, row, 1);
            model.setValueAt(author, row, 2);
            model.setValueAt(publisher, row, 3);

            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!");
        }
    }

    private void deleteBook() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to delete!");
            return;
        }
        books.remove(row);
        model.removeRow(row);
        clearFields();
    }

    private void searchBook() {
        String keyword = searchField.getText().toLowerCase();
        model.setRowCount(0);
        for (Book b : books) {
            if (b.title.toLowerCase().contains(keyword) || 
                b.author.toLowerCase().contains(keyword) || 
                b.publisher.toLowerCase().contains(keyword)) {
                model.addRow(new Object[]{b.id, b.title, b.author, b.publisher});
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        publisherField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystem1().setVisible(true));
    }
}
