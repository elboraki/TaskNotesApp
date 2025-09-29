-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 29, 2025 at 01:34 AM
-- Server version: 8.0.42-0ubuntu0.20.04.1
-- PHP Version: 7.4.3-4ubuntu2.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tasks_notes`
--

-- --------------------------------------------------------

--
-- Table structure for table `categorie`
--

CREATE TABLE `categorie` (
  `id` int NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categorie`
--

INSERT INTO `categorie` (`id`, `name`) VALUES
(1, 'Work'),
(2, 'Personal'),
(3, 'Study'),
(4, 'Travel'),
(5, 'Finance');

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `id` int NOT NULL,
  `body` text COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` int NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`id`, `body`, `category_id`, `user_id`) VALUES
(1, 'updated body', 3, 1),
(2, 'Buy milk, eggs, and bread after work.', 2, 2),
(3, 'Read the new Kafka documentation for tomorrow’s meeting.', 3, 3),
(4, 'Book hotel for next month’s conference.', 4, 4),
(5, 'Pay electricity bill before the 15th.', 5, 5),
(6, 'Review pull requests from backend team.', 1, 1),
(7, 'Plan weekend trip to Chefchaouen.', 2, 2),
(8, 'Research microservices architecture best practices.', 3, 3),
(9, 'Renew passport before September.', 4, 4),
(10, 'Transfer rent money to landlord.', 5, 5),
(11, 'Prepare meeting agenda for Monday.', 1, 1),
(12, 'Call the plumber for kitchen sink repair.', 2, 2),
(13, 'Complete Flask tutorial exercises.', 3, 3),
(14, 'Book train tickets for vacation.', 4, 4),
(15, 'Check credit card statement for unusual charges.', 5, 5),
(16, 'Finish API integration tests.', 1, 1),
(17, 'Buy birthday gift for Sarah.', 2, 2),
(18, 'Watch lecture on Spring Boot deployment.', 3, 3),
(19, 'Plan itinerary for Paris trip.', 4, 4),
(20, 'Update budget spreadsheet.', 5, 5),
(21, 'Draft proposal for new feature.', 1, 1),
(22, 'Clean the garage on Saturday.', 2, 2),
(23, 'Revise Go concurrency concepts.', 3, 3),
(24, 'Look for affordable flights to Madrid.', 4, 4),
(25, 'Pay car insurance premium.', 5, 5),
(26, 'Schedule code review session.', 1, 1),
(27, 'Organize bookshelf at home.', 2, 2),
(28, 'Write notes on REST API design.', 3, 3),
(29, 'Reserve Airbnb in Barcelona.', 4, 4),
(30, 'Deposit salary into savings account.', 5, 5),
(31, 'Update project documentation.', 1, 1),
(32, 'Buy fresh vegetables from market.', 2, 2),
(33, 'Study Docker networking.', 3, 3),
(34, 'Plan summer vacation to Italy.', 4, 4),
(35, 'Review monthly investment portfolio.', 5, 5),
(36, 'Fix bug in authentication module.', 1, 1),
(37, 'Call mom in the evening.', 2, 2),
(38, 'Learn about event-driven programming.', 3, 3),
(39, 'Check hotel reviews before booking.', 4, 4),
(40, 'Track expenses for this month.', 5, 5),
(41, 'Set up CI/CD pipeline for project.', 1, 1),
(43, 'Practice SQL joins and indexing.', 3, 3),
(44, 'Prepare packing list for trip.', 4, 4),
(45, 'Pay phone bill online.', 5, 5),
(46, 'Schedule team retrospective meeting.', 1, 1),
(47, 'Declutter old clothes for donation.', 2, 2),
(48, 'Revise Java streams and lambdas.', 3, 3),
(49, 'Check visa requirements for Germnay.', 4, 1),
(50, 'Submit tax return before deadline.', 5, 1),
(57, 'note body', 2, 5),
(58, 'note body', 2, 5),
(59, 'note body', 2, 5),
(60, 'note body', 2, 5),
(61, 'note body', 2, 5),
(62, 'note body', 2, 5),
(63, 'note body', 2, 5),
(64, 'note body', 2, 5),
(65, 'note body', 2, 5),
(66, 'note body', 2, 5),
(67, 'note body', 2, 5),
(68, 'note body', 2, 5),
(69, 'note body', 2, 5),
(70, 'note body', 2, 5),
(71, 'note body', 2, 5),
(72, 'note body', 2, 5),
(73, 'note body', 2, 5),
(74, 'note body', 2, 5),
(75, 'note body', 2, 5),
(76, 'note body', 2, 5);

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` int NOT NULL,
  `title` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `description` text COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`id`, `title`, `description`, `status`, `user_id`) VALUES
(1, 'Finish report', 'Complete the monthly sales report', 'Pending', 1),
(2, 'Code review', 'Review pull requests from the frontend team', 'In Progress', 2),
(3, 'Database backup', 'Run and verify the weekly database backup', 'Completed', 1),
(4, 'UI design update', 'Update the dashboard with the new UI guidelines', 'Pending', 3),
(5, 'Bug fix', 'Resolve login page timeout issue', 'In Progress', 4),
(6, 'Deploy to staging', 'Deploy the latest build to the staging server', 'Pending', 5),
(7, 'Write unit tests', 'Increase test coverage for the API', 'Completed', 2),
(8, 'Client meeting', 'Discuss requirements with the client', 'In Progress', 3),
(9, 'Security audit', 'Check application for vulnerabilities', 'Pending', 4),
(10, 'Optimize queries', 'Improve performance of SQL queries', 'Completed', 1),
(11, 'Update dependencies', 'Upgrade all outdated npm packages', 'In Progress', 2),
(12, 'Refactor code', 'Clean up legacy code in the backend', 'Pending', 5),
(13, 'Prepare presentation', 'Slides for the project review meeting', 'Completed', 3),
(14, 'Test payment gateway', 'Ensure PayPal and Stripe work correctly', 'Pending', 1),
(15, 'Fix CSS bugs', 'Resolve layout issues in mobile view', 'In Progress', 4),
(16, 'API documentation', 'Write documentation for public API', 'Completed', 2),
(17, 'Migrate database', 'Move from MySQL to PostgreSQL', 'Pending', 5),
(18, 'Implement login', 'Add authentication to the app', 'Completed', 3),
(19, 'Performance monitoring', 'Set up New Relic for server monitoring', 'Pending', 4),
(20, 'Create mockups', 'Design wireframes for new features', 'In Progress', 1),
(21, 'Email campaign', 'Send newsletter to all subscribers', 'Pending', 2),
(22, 'Data cleanup', 'Remove duplicate records from the DB', 'Completed', 5),
(23, 'Add search feature', 'Implement search bar with filters', 'In Progress', 3),
(24, 'Fix broken links', 'Update or remove dead URLs', 'Pending', 4),
(25, 'User feedback', 'Collect and analyze feedback from beta testers', 'Completed', 1),
(26, 'Update readme', 'Improve the project README file', 'Pending', 2),
(27, 'Docker setup', 'Containerize the app using Docker', 'In Progress', 5),
(28, 'Push to production', 'Deploy the app to production servers', 'Completed', 3),
(29, 'Session management', 'Improve handling of user sessions', 'Pending', 4),
(30, 'Analytics dashboard', 'Add metrics for user activity', 'In Progress', 1),
(31, 'Translation', 'Add French and Spanish translations', 'Completed', 2),
(32, 'Cache optimization', 'Use Redis for caching', 'Pending', 5),
(33, 'Feature toggle', 'Implement feature flag system', 'In Progress', 3),
(34, 'Onboarding flow', 'Create guided onboarding for new users', 'Pending', 4),
(35, 'Social login', 'Allow login via Google and Facebook', 'Completed', 1),
(36, 'Mobile testing', 'Test app on various mobile devices', 'Pending', 2),
(37, 'Logging improvements', 'Enhance logging with more details', 'In Progress', 5),
(38, 'Image optimization', 'Reduce image sizes for faster load times', 'Completed', 3),
(39, 'A/B testing', 'Experiment with different UI layouts', 'Pending', 4),
(40, 'Webhook integration', 'Set up webhook endpoints', 'In Progress', 1),
(41, 'API rate limiting', 'Prevent abuse with rate limits', 'Completed', 2),
(42, 'Push notifications', 'Notify users about updates', 'Pending', 5),
(43, 'Bug triage', 'Sort and prioritize bug reports', 'In Progress', 3),
(45, 'Dark mode', 'Add dark theme option', 'Pending', 1),
(46, 'Backup automation', 'Schedule daily automatic backups', 'In Progress', 2),
(47, 'Accessibility fixes', 'Improve WCAG compliance', 'Completed', 5),
(48, 'Version bump', 'Release new minor version', 'Pending', 3),
(49, 'Search indexing', 'Implement Elasticsearch indexing', 'In Progress', 4);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `login` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `date_creation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `date_creation`) VALUES
(1, 'jdoe', 'Pass@123', '2025-08-14 08:30:00'),
(2, 'asmith', 'qwerty!2024', '2025-08-13 13:15:00'),
(3, 'm.ali', 'Welcome#99', '2025-08-12 17:45:00'),
(4, 'lina.k', 'MyPass_456', '2025-08-10 10:00:00'),
(5, 'user_test', 'Abc123$$', '2025-08-09 06:20:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login_constraints` (`login`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `notes_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categorie` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
