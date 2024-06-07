package com.example.fileencryptor;

import java.io.*;
import java.util.Scanner;

public class FileEncryptorDecryptor {

    private static final byte XOR_KEY = 0x5A; // XOR key for encryption/decryption

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("File Encryptor and Decryptor");
        System.out.println("1. Encrypt File");
        System.out.println("2. Decrypt File");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter the path of the file to encrypt: ");
                String inputFileEncrypt = scanner.nextLine();
                System.out.print("Enter the path for the encrypted file (leave blank to save in the current directory): ");
                String outputFileEncrypt = scanner.nextLine();
                if (outputFileEncrypt.isEmpty()) {
                    outputFileEncrypt = getDefaultOutputFilePath(inputFileEncrypt, "_encrypted");
                }
                try {
                    xorEncryptDecryptFile(inputFileEncrypt, outputFileEncrypt);
                    System.out.println("File encrypted successfully!");
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
                break;
            case 2:
                System.out.print("Enter the path of the file to decrypt: ");
                String inputFileDecrypt = scanner.nextLine();
                System.out.print("Enter the path for the decrypted file (leave blank to save in the current directory): ");
                String outputFileDecrypt = scanner.nextLine();
                if (outputFileDecrypt.isEmpty()) {
                    outputFileDecrypt = getDefaultOutputFilePath(inputFileDecrypt, "_decrypted");
                }
                try {
                    xorEncryptDecryptFile(inputFileDecrypt, outputFileDecrypt);
                    System.out.println("File decrypted successfully!");
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
                break;
            case 3:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }

        scanner.close();
    }

    public static void xorEncryptDecryptFile(String inputFile, String outputFile) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            int data;
            while ((data = bis.read()) != -1) {
                bos.write(data ^ XOR_KEY); // Apply XOR operation with the key
            }
        }
    }

    private static String getDefaultOutputFilePath(String inputFilePath, String suffix) {
        File inputFile = new File(inputFilePath);
        String parentDirectory = inputFile.getParent();
        String fileName = inputFile.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex) + suffix + fileName.substring(dotIndex);
        } else {
            fileName = fileName + suffix;
        }
        if (parentDirectory != null) {
            return parentDirectory + File.separator + fileName;
        } else {
            return fileName; // If no parent directory, save in the current directory
        }
    }
}
