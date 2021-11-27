# Pcode-Similarity
Algorithm for calculating similarity between function and library function.

## 简介
目前的二进制函数匹配，在工业界主要有：
- Bindiff：用于diff
- lumina：用于搜索函数，利用flirt匹配方案
等，其中lumina和我们的预期目标一致，但lumina的具体匹配方案非常简陋，无法处理语义一致的情况。
另外，其匹配方案大多无法考虑到调用关系，例如所有函数中，只有函数A调用了函数B和C，那么当出现一个函数调用了函数B和C，不考虑其他任何特征就可确定其一定为函数A。
出于这种考虑，我们计划研究一种新的二进制函数匹配方案。

## 目标
提出新的二进制函数匹配方案，具备以下技术优势：
- 可用于大数据场景：对于超大函数库搜索，必须保持可接受的性能与准确率。
- 考虑调用关系：函数调用关系要作为特征之一
- 跨编译器：对于编译器引入的具体二进制函数差距不敏感
- 方案不过度复杂：方案的实现难度不能太高（对比order matters，腾讯科恩实验室的基于图神经网络的二进制函数匹配，其具体方案过于复杂，实现难度大，调参难度大）
- *减少条件编译影响：对于条件编译影响函数内容的情况，尽可能不敏感
- *跨平台
- *能够识别漏洞是否已经修补：对于包含漏洞特征的函数，考虑漏洞特征，如果漏洞已经被修复，能够正常识别到漏洞函数虽然存在，但是已经被修复。
其中星号内容为次要目标，优先满足主要目标

## Re-optimization 
Paper: [Similarity of Binaries through re-Optimization](http://www.cs.technion.ac.il/~yanivd/pldi17/pldi17_GitZ.pdf)
注：本仓库实现对原论文一些公式及其细节进行优化

### 实现方案
- 首先，通过ghidra获取库中所有函数的strands，对于给定的库外target函数提取strands
- 其次，统计已知库函数每个strands出现的次数和概率，来为相似性得分加以权重，出现少的strands在得分权重更高（更稀有），出现多的strands得分权重更低。
- 基于此理论基础：来计算相似性的绝对得分：给定Target与Query（Lib中的其中一个Function）：取交集获取到所有的strand，统计每个strands出现的次数count，除以Lib中所有函数的strands个数的总和P，得到交集中每个strand出现的概率，记为概率Ps，将所有的概率取倒数1/Ps，再经过log函数压缩求和（原论文没有加Log）
- 根据交集可以得到Target与Query的绝对相似得分，该得分存在一个很不直观的常数（即不同的函数进行计算得到绝对相似得分的分值不一样，这完全取决于strand的属性，这样存在相似性得分高的实际相似性很低的情况，因为得分的概率完全取决于strand的概率），为此，我们继续优化该数值，引入相对相似性得分的概念：
  + 相对相似性得分：Target与Query的绝对相似得分/sqrt(Target自相似性得分*Query自相似性得分)
- 根据相对相似性得分可以看出，此时计算出的相似性是一个从0到1的归一化数值，相似性越大越接近于1，反之接近于0。上述我们计算可以得到Target与Query的绝对相似得分，现在针对给定的Target函数，计算自己和自己的相似性得分，以及需要计算Query函数的自己和自己的相似性得分。带入计算公式，得到SR。
- 由于计算的重复度很高，我们需要把重复计算和Lib库存储在数据库中进行查询，从而加速计算过程。具体设计包括以下三个表格：
  + 将Lib库中所有的函数的strands提取出来进行hash处理存储在一张表中，包括字段：函数标识，函数名，函数的strands，函数本身hash
  + 将Lib中所有strand统计放在第二张表中，包括字段：strand标识，strand哈希，计数，概率倒数
  + 将所有Lib中的Query函数绝对自相似得分计算存储，包括字段：函数标识，自相似得分

### Pcode
Pcode: Ghidra逆向软件的一种中间语言

[Ghidra进阶版](https://github.com/JackHCC/ghidracraft)

## 开源说明
本项目由[本人](https://github.com/JackHCC)与同事[ios](https://github.com/ioo0s)在星阑科技有限公司实习期间的所做，现由于公司不打算做该方向的研究，并且将整个部门拆除，在此开源学习。

![xlkj](./xlkj.jpg)

