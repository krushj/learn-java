CODING DSA QUESTIONS

Arrays & Strings

1. Two Sum
Hint: Use HashMap to store complement (target - current number)
LeetCode: https://leetcode.com/problems/two-sum/
2. Find First Non-Repeating Character
Hint: Two passes - first count frequency, second find first char with freq=1
LeetCode: https://leetcode.com/problems/first-unique-character-in-a-string/
3. Longest Substring Without Repeating Characters
Hint: Sliding window with HashMap, track start pointer
LeetCode: https://leetcode.com/problems/longest-substring-without-repeating-characters/
4. Move Zeroes
Hint: Two pointers - one for non-zero position, iterate and swap
LeetCode: https://leetcode.com/problems/move-zeroes/
5. Rotate Array
Hint: Reverse entire array, then reverse first k elements, then reverse rest
LeetCode: https://leetcode.com/problems/rotate-array/
6. Check Palindrome
Hint: Two pointers from start and end moving towards center
LeetCode: https://leetcode.com/problems/valid-palindrome/
7. Anagram Check
Hint: Sort both strings or use frequency count
LeetCode: https://leetcode.com/problems/valid-anagram/
8. Merge Two Sorted Arrays
Hint: Two pointers, compare and merge from end if in-place
LeetCode: https://leetcode.com/problems/merge-sorted-array/
9. Find Missing Number
Hint: Sum formula (n*(n+1)/2) or XOR approach
LeetCode: https://leetcode.com/problems/missing-number/
10. First Missing Positive Integer
Hint: Use array indices as hash, place each number at index = number-1
LeetCode: https://leetcode.com/problems/first-missing-positive/
11. Remove Duplicates from Array/String
Hint: Two pointers - one for unique position
LeetCode: https://leetcode.com/problems/remove-duplicates-from-sorted-array/
12. Reverse Words in a String
Hint: Split by space, reverse array, join OR reverse entire then reverse each word
LeetCode: https://leetcode.com/problems/reverse-words-in-a-string/

HashMap / Frequency

13. Top K Frequent Elements
Hint: HashMap for frequency + PriorityQueue (min heap of size k)
LeetCode: https://leetcode.com/problems/top-k-frequent-elements/
14. Sort Array by Frequency
Hint: HashMap for count, then sort using custom comparator
LeetCode: https://leetcode.com/problems/sort-array-by-increasing-frequency/
15. Count Frequency of Characters
Hint: HashMap or int[26] for lowercase letters
LeetCode: https://leetcode.com/problems/sort-characters-by-frequency/
16. First Unique Element
Hint: LinkedHashMap to maintain insertion order
LeetCode: https://leetcode.com/problems/first-unique-character-in-a-string/
17. Group Anagrams
Hint: Sort each word as key, group words with same sorted key
LeetCode: https://leetcode.com/problems/group-anagrams/
18. Find Duplicates
Hint: HashSet or mark visited indices as negative
LeetCode: https://leetcode.com/problems/find-all-duplicates-in-an-array/

Sorting

19. Sort List/Array
Hint: Collections.sort() or Arrays.sort() with custom comparator
LeetCode: https://leetcode.com/problems/sort-an-array/
20. Sort Map by Value
Hint: Convert to List of entries, sort using comparator, create LinkedHashMap
21. Sort Intervals by Start Time
Hint: Comparator.comparingInt(interval -> interval[0])
LeetCode: https://leetcode.com/problems/merge-intervals/
22. Custom Comparator Implementation
Hint: Implement Comparator interface or use lambda

Linked List (Basic)

23. Reverse Linked List
Hint: Three pointers: prev, current, next
LeetCode: https://leetcode.com/problems/reverse-linked-list/
24. Detect Cycle
Hint: Floyd's algorithm - slow and fast pointer
LeetCode: https://leetcode.com/problems/linked-list-cycle/
25. Insert/Delete Node
Hint: Track previous node, adjust pointers
LeetCode: https://leetcode.com/problems/delete-node-in-a-linked-list/

Trees (Basic)

26. Inorder Traversal
Hint: Recursive: left -> root -> right OR iterative with stack
LeetCode: https://leetcode.com/problems/binary-tree-inorder-traversal/
27. Maximum Depth of Binary Tree
Hint: Recursive: 1 + max(left depth, right depth)
LeetCode: https://leetcode.com/problems/maximum-depth-of-binary-tree/
28. Check if Tree is Balanced
Hint: Check height difference of left and right subtree ≤ 1 at each node
LeetCode: https://leetcode.com/problems/balanced-binary-tree/